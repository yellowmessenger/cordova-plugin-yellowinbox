package ai.yellow.agentsdk.models;

import com.yellowmessenger.datalayer.vo.User;

public class ClientModel {
  User user;
  String accessToken;

  public ClientModel(String accessToken, User user) {
    this.user = user;
    this.accessToken = accessToken;
  }
}
