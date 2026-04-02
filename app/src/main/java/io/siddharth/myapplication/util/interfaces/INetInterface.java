package io.siddharth.myapplication.util.interfaces;

import io.siddharth.myapplication.domain.model.LoginResponseModel;

public interface INetInterface {
    void loginFinish(LoginResponseModel loginResponseModel);
    void downloadStudentList();
    void notAvailable();
    void nodeDetail();
    void contentNode();
    void metaData();
    void mspOrganizationDetails();
    void ispOrganizationDetails();
    void onError(int requestId);
}
