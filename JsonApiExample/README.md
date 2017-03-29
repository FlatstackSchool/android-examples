JsonApi Android Example
=======================================

This example build on top of [retrofit2](https://github.com/square/retrofit) + [moshi](https://github.com/square/moshi) + [moshi-jsonapi](https://github.com/kamikat/moshi-jsonapi)

Simple ToDo Android application.

You can:
* Register
* Login
* See ToDo List
* Create new ToDo
* Update existing Todo

you can't logout :p

# How can I add my own requests?
### Parse answer
JsonApi dictates following answer format:

```
{
  "data": {
    "id": "5",
    "type": "todo-items",
    "links": {
      "self": "http://localhost:3000/v1/todo-items/5"
    },
    "attributes": {
      "title": "Meggings 8-bit irony tousled mlkshk neutra crucifix lumbersexual kogi.",
      "text": "Meh wolf whatever salvia cardigan seitan paleo. Health poutine listicle goth scenester kitsch fanny pack. Narwhal cold-pressed hashtag goth umami.",
    }
  }
}
```

Good news, you don't have to parse such a _strange_ json by yourself. Just create following object:
```
@JsonApi(type = "todo-items")
public class ToDo extends moe.banana.jsonapi2.Resource {
    String title;
    String text;

    public ToDo(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
```
First of all your object should extends from `Resource` class.
As you can see `ToDo`'s object fields coincide with json's keys under `attributes` fields.
If you want object field name and json key name to differ then use `@Json(name="first-name") String name` annotation.
Also `type` in json and type inside `@JsonApi(type = )` annotation of `ToDo` class should be the same.

After than just wrap your class by `ObjectDocument<ToDo>` in case of single object or `ArrayDocument<ToDo>` in case of array to parse answer.

So your Retrofit will look like:

```
@GET("todo-items/") Observable<moe.banana.jsonapi2.ArrayDocument<ToDo>> todos();
```


### Prepare request

When you do POST/PUT/PATCH then probably you need to send some info in request body. JsonApi dictates following structure:
```
{
  "data": {
    "type": "todo-items",
    "attributes": {
      "title": "Nice title here",
      "text": "Text you never seen before ;)"
    }
  }
}
```
which is the same format as above for answers. So create object following rules above.

Now, your retrofit request will look like:
```
@POST("todo-items/") Observable<ResponseBody> createToDo(@Body moe.banana.jsonapi2.Document<ToDo> todo);
```


[<img src="http://www.flatstack.com/logo.svg" width="100"/>](http://www.flatstack.com)

