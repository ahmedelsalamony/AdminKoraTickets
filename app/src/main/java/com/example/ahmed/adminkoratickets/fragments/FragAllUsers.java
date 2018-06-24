package com.example.ahmed.adminkoratickets.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.adminkoratickets.R;
import com.example.ahmed.adminkoratickets.adapter.RecyclerAdapter;
import com.example.ahmed.adminkoratickets.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragAllUsers extends Fragment {

    View rootView;
    //  ProgressDialog progressDialog;
    RecyclerView mRecyclerView;
    List<UserModel> data;
    DatabaseReference userRrf;
    RecyclerAdapter adapter;
    FloatingActionButton btn_add_user;
    public static final String TAG = FragAllUsers.class.getSimpleName();
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    EditText et_userName, et_email, et_password, et_phone;
    Button btn_register;
    ProgressDialog register_progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.allusers_frag, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("انتظر من فضلك ...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_users);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        data = new ArrayList<>();

        btn_add_user = rootView.findViewById(R.id.btn_add_user);


        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_user);
                dialog.setTitle("...اضافة مشجع جديد");
                dialog.show();

                et_userName = dialog.findViewById(R.id.et_userName);
                et_email = dialog.findViewById(R.id.et_email);
                et_password = dialog.findViewById(R.id.et_pass);
                et_phone = dialog.findViewById(R.id.et_phone);
                btn_register = dialog.findViewById(R.id.btn_register);


                btn_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        register_progress = new ProgressDialog(getActivity());
                        register_progress.setMessage("انتظر من فضلك ...");
                        register_progress.setCancelable(false);
                        register_progress.show();

                        createAccount(et_email.getText().toString(), et_password.getText().toString());

                    }
                });


            }
        });


        userRrf = FirebaseDatabase.getInstance().getReference().child("users");
        userRrf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    UserModel userModel = new UserModel(

                            ds.child("displayName").getValue().toString(),
                            ds.child("email").getValue().toString(),
                            ds.child("phone").getValue().toString()
                    );

                    data.add(userModel);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new RecyclerAdapter(data, R.layout.user_item, UserViewHolder.class,
                new RecyclerAdapter.AdapterInterface<UserModel, UserViewHolder>() {

                    @Override
                    public void fillData(final UserModel dataModel, final UserViewHolder viewHolder) {


                        viewHolder.setUserName(dataModel.getName().toString());
                        viewHolder.setEmail(dataModel.getEmail().toString());
                        viewHolder.setPhone(dataModel.getPhone().toString());


                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                builder.setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User clicked OK button


                                        userRrf.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                    try {
                                                        if (ds.child("displayName").getValue().toString().equals(data.get(viewHolder.getAdapterPosition()).getEmail())) {
                                                            Log.d(TAG, "fvxvxc");
                                                            data.remove(viewHolder.getLayoutPosition());
                                                            userRrf.child(ds.getKey()).removeValue();
                                                            mRecyclerView.removeViewAt(viewHolder.getLayoutPosition());
                                                            adapter.notifyDataSetChanged();

                                                        }

                                                    } catch (ArrayIndexOutOfBoundsException e) {
                                                        e.getMessage();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                });
                                builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                    dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.setMessage("هل تريد حذف هذا المشجع");
                                dialog.show();

                            }
                        });
                    }
                }
        );
        mRecyclerView.setAdapter(adapter);

        return rootView;
    }

    private void writeNewUser(String userId, String param_displayName, String param_email,
                              String param_password, String param_phone) {

        HashMap<String, String> hashUser = new HashMap<>();
        hashUser.put("displayName", param_displayName);
        hashUser.put("email", param_email);
        hashUser.put("password", param_password);
        hashUser.put("phone", param_phone);

        mDatabase.child("users").child(userId).setValue(hashUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                }
            }
        });
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            writeNewUser(user.getUid(), et_userName.getText().toString(), et_email.getText().toString()
                                    , et_password.getText().toString(), et_phone.getText().toString());
                            Toast.makeText(getActivity(), "تمت الاضافة بنجاح",
                                    Toast.LENGTH_SHORT).show();

                            register_progress.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "من فضلك اعد المحاولة",
                                    Toast.LENGTH_SHORT).show();
                            register_progress.dismiss();
                        }

                    }
                });
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setUserName(String userName) {
            TextView tv_userName = mView.findViewById(R.id.tv_user_name);
            tv_userName.setText(userName);
        }

        public void setEmail(String email) {
            TextView tv_email = mView.findViewById(R.id.tv_email);
            tv_email.setText(email);
        }

        public void setPhone(String phone) {
            TextView tv_phone = mView.findViewById(R.id.tv_phone);
            tv_phone.setText(phone);
        }

    }
}
