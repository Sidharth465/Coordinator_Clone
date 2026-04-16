package io.siddharth.myapplication.activity;

import android.app.DatePickerDialog;
import android.content.Intent;import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.bumptech.glide.Glide;
import io.siddharth.myapplication.R;
import io.siddharth.myapplication.databinding.ActivityStudentRegBinding;
import io.siddharth.myapplication.domain.model.StudentModel;
import io.siddharth.myapplication.util.Utils;
import com.yalantis.ucrop.UCrop;
import java.io.File;
import java.util.Calendar;

public class StudentRegActivity extends AppCompatActivity {

    private ActivityStudentRegBinding binding;
    private StudentModel student;
    private Uri tempImageUri;
    private String imageName;

    // Modern Activity Results API
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    startCrop(tempImageUri);
                }
            }
    );

    private final ActivityResultLauncher<Intent> cropLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri resultUri = UCrop.getOutput(result.getData());
                    if (resultUri != null) {
                        binding.stuImage.setImageURI(resultUri);
                        // Save image logic using Utils
                        Utils.getInstance().saveImageFromUri(this, resultUri, imageName);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        student = (StudentModel) getIntent().getSerializableExtra("StudentModel");

        setupToolbar();
        bindData();
        setupListeners();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        binding.toolbar.toolbarTitle.setText(student == null ? "New Student" : "Edit Profile");
        binding.toolbar.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void bindData() {
        if (student != null) {
            binding.etFirstName.setText(student.getName());
            binding.etAdmissionNo.setText(student.getAdmissionNo());
            binding.etDob.setText(student.getDob());
            // Set Gender
            if ("M".equalsIgnoreCase(student.getGender())) binding.rbMale.setChecked(true);
            else if ("F".equalsIgnoreCase(student.getGender())) binding.rbFemale.setChecked(true);

            // Load Image
            String imagePath = Utils.getInstance().getStudentImagePath(student.getImageName());
            Glide.with(this).load(imagePath).placeholder(R.drawable.image_place_holder).into(binding.stuImage);
        }
    }

    private void setupListeners() {
        binding.btnCamera.setOnClickListener(v -> launchCamera());
        binding.etDob.setOnClickListener(v -> showDatePicker());
        binding.btnSubmit.setOnClickListener(v -> saveStudent());
    }

    private void launchCamera() {
        imageName = "stu_" + System.currentTimeMillis() + ".jpg";
        File file = new File(getExternalCacheDir(), imageName);
        tempImageUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempImageUri);
        cameraLauncher.launch(intent);
    }

    private void startCrop(Uri uri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + imageName));
        UCrop.Options options = new UCrop.Options();
        options.setCircleDimmedLayer(true);

        Intent intent = UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(500, 500)
                .getIntent(this);

        cropLauncher.launch(intent);
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            String date = year + "-" + (month + 1) + "-" + day;
            binding.etDob.setText(date);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveStudent() {
        // Validation and Save Logic (Insert/Update to Room)
        // This should ideally call a method in a StudentRegViewModel
    }
}