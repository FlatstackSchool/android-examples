package flatstack.com.roomarchcomponents.data.entity;

import moe.banana.jsonapi2.JsonApi;
import moe.banana.jsonapi2.Resource;

@JsonApi(type = "users")
public class User extends Resource {
    int id;
    String name;
}
