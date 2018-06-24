package com.example.ahmed.adminkoratickets.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ahmed.adminkoratickets.R;
import com.example.ahmed.adminkoratickets.models.MatchesModel;
import com.example.ahmed.adminkoratickets.models.PriceModel;
import com.example.ahmed.adminkoratickets.models.ticketInfoModel;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

import java.util.ArrayList;
import java.util.Calendar;

public class FragTicketsMatches extends Fragment {

    View rootView;
    TextView tv_maches_success;
    TextInputEditText st_team,nd_team,num_tickets_st,num_tickets_nd,num_tickets_th,price_tickets_st,price_tickets_nd,price_tickets_th,stadium;
    Button btn_time,btn_date,btn_add_match,btn_add_tickets;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList<MatchesModel> arr;
    ProgressDialog progressDialog;
    TextInputLayout num_layout_st,price_layout_st,num_layout_nd,price_layout_nd,num_layout_th,price_layout_th;
    private DatabaseReference mDatabase;
    public static final String TAG = FragTicketsMatches.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.ticketsmatches_frag,container,false);
       tv_maches_success = rootView.findViewById(R.id.tv_match_success);
       st_team = rootView.findViewById(R.id.st_team);
       nd_team = rootView.findViewById(R.id.sd_team);
       btn_date = rootView.findViewById(R.id.btn_date);
       btn_time = rootView.findViewById(R.id.btn_time);
       num_tickets_st = rootView.findViewById(R.id.num_first);
       num_tickets_nd = rootView.findViewById(R.id.num_second);
       num_tickets_th = rootView.findViewById(R.id.num_third);
       price_tickets_st = rootView.findViewById(R.id.price_first);
       price_tickets_nd = rootView.findViewById(R.id.price_second);
       price_tickets_th = rootView.findViewById(R.id.price_third);
       btn_add_match = rootView.findViewById(R.id.btn_add_match);
       stadium = rootView.findViewById(R.id.stadium);
       num_layout_st = rootView.findViewById(R.id.textInputLayout3);
       price_layout_st = rootView.findViewById(R.id.textInputLayout6);
       num_layout_nd = rootView.findViewById(R.id.textInputLayout7);
       price_layout_nd = rootView.findViewById(R.id.textInputLayout4);
       num_layout_th = rootView.findViewById(R.id.textInputLayout5);
       price_layout_th = rootView.findViewById(R.id.textInputLayout8);
        btn_add_tickets = rootView.findViewById(R.id.btn_add_tickets);


       btn_date.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               // Get Current Date
               final Calendar c = Calendar.getInstance();
               mYear = c.get(Calendar.YEAR);
               mMonth = c.get(Calendar.MONTH);
               mDay = c.get(Calendar.DAY_OF_MONTH);


               DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                       new DatePickerDialog.OnDateSetListener() {

                           @Override
                           public void onDateSet(DatePicker view, int year,
                                                 int monthOfYear, int dayOfMonth) {

                               btn_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                           }
                       }, mYear, mMonth, mDay);
               datePickerDialog.show();
           }
       });

       btn_time.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               // Get Current Time
               final Calendar c = Calendar.getInstance();
               mHour = c.get(Calendar.HOUR_OF_DAY);
               mMinute = c.get(Calendar.MINUTE);

               // Launch Time Picker Dialog
               TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                       new TimePickerDialog.OnTimeSetListener() {

                           @Override
                           public void onTimeSet(TimePicker view, int hourOfDay,
                                                 int minute) {

                               btn_time.setText(hourOfDay + ":" + minute);
                           }
                       }, mHour, mMinute, false);
               timePickerDialog.show();
           }
       });
    arr = new ArrayList<>();

       btn_add_match.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               FirebaseDatabase database = FirebaseDatabase.getInstance();
               final DatabaseReference myRef = database.getReference("A_matches");



                       progressDialog = new ProgressDialog(getActivity());
                       progressDialog.setMessage("انتظر من فضلك");
                       progressDialog.setCancelable(false);
                       progressDialog.show();


                       MatchesModel matchesModel1 = new MatchesModel(""
                               ,st_team.getText().toString(),nd_team.getText().toString(),btn_date.getText().toString()
                               , btn_time.getText().toString(),stadium.getText().toString());
                       arr.add(matchesModel1);

                       myRef.push().setValue(matchesModel1, new DatabaseReference.CompletionListener() {
                           @Override
                           public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                               progressDialog.dismiss();
                               tv_maches_success.setVisibility(View.VISIBLE);
                               num_layout_st.setVisibility(View.VISIBLE);
                               price_layout_st.setVisibility(View.VISIBLE);
                               num_layout_nd.setVisibility(View.VISIBLE);
                               price_layout_nd.setVisibility(View.VISIBLE);
                               num_layout_th.setVisibility(View.VISIBLE);
                               price_layout_th.setVisibility(View.VISIBLE);
                               btn_add_tickets.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                           }
                       });
           }
       });

       btn_add_tickets.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               /////////////////////////////////////for add tickets///////////////////////////////////////////////
               FirebaseDatabase ticketdatabase = FirebaseDatabase.getInstance();
               final  DatabaseReference ticket_ref = ticketdatabase.getReference("A_tickets");

               mDatabase = FirebaseDatabase.getInstance().getReference();
               String match_Id = mDatabase.child("A_matches").push().getKey();
               Log.d(TAG,mDatabase.child("A_matches").push().getKey());

               ticketInfoModel ticketModel_st = new ticketInfoModel("",match_Id,"First");
               ticketInfoModel ticketModel_nd = new ticketInfoModel("",match_Id,"Second");
               ticketInfoModel ticketModel_th = new ticketInfoModel("",match_Id,"Third");

               for (int count = 0 ; count < Integer.parseInt(num_tickets_st.getText().toString());count++){

                   ticket_ref.push().setValue(ticketModel_st, new DatabaseReference.CompletionListener() {
                       @Override
                       public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                       }
                   });

               }

               for (int count = 0 ; count < Integer.parseInt(num_tickets_nd.getText().toString());count++){

                   ticket_ref.push().setValue(ticketModel_nd, new DatabaseReference.CompletionListener() {
                       @Override
                       public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                       }
                   });

               }


               for (int count = 0 ; count < Integer.parseInt(num_tickets_th.getText().toString());count++){

                   ticket_ref.push().setValue(ticketModel_th, new DatabaseReference.CompletionListener() {
                       @Override
                       public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                       }
                   });

               }

               /////////////////////////////////////for price of tickets///////////////////////////////////////////////

               FirebaseDatabase pricedatabase = FirebaseDatabase.getInstance();
               final  DatabaseReference ticket_price_ref = pricedatabase.getReference("A_price");


               PriceModel priceModel_st = new PriceModel("First",price_tickets_st.getText().toString(),match_Id);
               PriceModel priceModel_nd = new PriceModel("Second",price_tickets_nd.getText().toString(),match_Id);
               PriceModel priceModel_th = new PriceModel("Third",price_tickets_th.getText().toString(),match_Id);


               ticket_price_ref.push().setValue(priceModel_st, new DatabaseReference.CompletionListener() {
                   @Override
                   public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                   }
               });

               ticket_price_ref.push().setValue(priceModel_nd, new DatabaseReference.CompletionListener() {
                   @Override
                   public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                   }
               });
               ticket_price_ref.push().setValue(priceModel_th, new DatabaseReference.CompletionListener() {
                   @Override
                   public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                   }
               });

               Toast.makeText(getActivity(), "تمت اضافة التذاكر بنجاح", Toast.LENGTH_SHORT).show();

           }
       });
       return rootView;
    }


}
