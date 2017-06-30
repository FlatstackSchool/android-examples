package com.flatstack.android;

import android.content.Context;
import android.content.Intent;

import com.flatstack.android.models.ToDo;
import com.flatstack.android.todo_details.CreateToDoScreen;
import com.flatstack.android.todo_details.UpdateToDoScreen;
import com.flatstack.android.todo_list.TodosListScreen;

/**
 * Created by ereminilya on 29/3/17.
 */
public class Navigator {

    public static void createToDoAndReturnBack(TodosListScreen todosScreen, int requestCode) {
        todosScreen.startActivityForResult(new Intent(todosScreen, CreateToDoScreen.class), requestCode);
    }

    public static void toDos(Context context) {
        Intent intent = new Intent(context, TodosListScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void signIn(Context context) {
        Intent intent = new Intent(context, SignInScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void todoDetails(Context context, ToDo todo) {
        context.startActivity(UpdateToDoScreen.newInstance(context, todo));
    }

    public static void createAccount(Context context) {
        context.startActivity(new Intent(context, RegistrationScreen.class));
    }
}