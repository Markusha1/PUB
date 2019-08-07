package com.example.pub.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pub.DI.AppModule;
import com.example.pub.DI.DaggerDescribeComponent;
import com.example.pub.DI.DescribeComponent;
import com.example.pub.DI.DescriptionModule;
import com.example.pub.Models.Detail;
import com.example.pub.Presenters.DescribePresenter;
import com.example.pub.R;
import com.example.pub.Utilities.Constants;
import com.example.pub.Utilities.FetchAddressIntentService;
import com.example.pub.Utilities.GpsSettingsDialog;
import com.example.pub.Utilities.PhotoDialog;
import com.example.pub.views.DescribeView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import static com.example.pub.BuildConfig.APPLICATION_ID;


public class DescribeActivity extends MvpAppCompatActivity implements DescribeView, PhotoDialog.PhotoClickListener {
    private Button DateButton;
    private Button TimeButton;
    private Button TakePhotoButton, LocationButton;
    private Button CategoryButton;
    private TextView MyLocation;
    private String DescrText;
    private EditText DescribEditText, SummText;
    private String Money;
    private ImageView mPhoto;
    private String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_GALLERY_PHOTO = 23;
    static final int REQUEST_CATEGORY = 2;
    private FusedLocationProviderClient client;
    @InjectPresenter
    public DescribePresenter presenter;
    @Inject
    Calendar calendar;
    @Inject
    GoogleApiClient mClient;
    private Location lastLocation;
    private LocationCallback locationCallback;
    String currentPhotoPath;
    private DialogFragment dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        DescribeComponent component = DaggerDescribeComponent.builder().appModule(new AppModule(this)).descriptionModule(new DescriptionModule()).build();
        component.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_detail);
        client = LocationServices.getFusedLocationProviderClient(this);
        presenter.initDetail((Detail) getIntent().getSerializableExtra("describedetail"));
        File photoFile = presenter.getPhotoFile(this);
        mPhoto = findViewById(R.id.photo);
        mPhoto.setOnClickListener(v -> {
            if(mPhoto.getDrawable() != null) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentPhotoPath)));
            }
    });

        MyLocation = findViewById(R.id.location);
        TakePhotoButton = findViewById(R.id.camera_button);
        LocationButton = findViewById(R.id.add_location);
        LocationButton.setOnClickListener(v -> checkLocationPermission());
        boolean canTakePhoto = photoFile != null &&
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(getApplication().getPackageManager()) != null;
        TakePhotoButton.setEnabled(canTakePhoto);
        TakePhotoButton.setOnClickListener(v -> checkCameraPermission());
        DescribEditText = findViewById(R.id.description);
        DescribEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DescrText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        CategoryButton = findViewById(R.id.category_button);
        CategoryButton.setOnClickListener(g -> {
            Intent intent = new Intent(this, CategoryActivity.class);
            startActivityForResult(intent, REQUEST_CATEGORY);
        });

        SummText = findViewById(R.id.summ);
        SummText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Money = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        DateButton = findViewById(R.id.date_picker);
        DateButton.setOnClickListener(g -> presenter.showDatePicker());
        TimeButton = findViewById(R.id.time_picker);
        TimeButton.setOnClickListener(g -> presenter.showTimePicker());
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    lastLocation = location;
                    startIntentService();
                }
            }
        };
        initDateTime();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_detail) {
            presenter.onApplyClick(DescrText,
                    CategoryButton.getText().toString(), DateButton.getText().toString(),
                    TimeButton.getText().toString(), Money, MyLocation.getText().toString(), currentPhotoPath);
        }
        return super.onOptionsItemSelected(item);
    }

    public void initDateTime() {
        DateButton.setText(android.text.format.DateFormat.format("dd MM yy", calendar.getTime()));
        TimeButton.setText(android.text.format.DateFormat.format("kk:mm", calendar.getTime()));
    }

    @Override
    public void showCalendar() {
        new DatePickerDialog(this, d,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    @Override
    public void showClock() {
        new TimePickerDialog(this, t,
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.HOUR_OF_DAY),
                true)
                .show();
    }

    @Override
    public void sendResult(Detail detail) {
        Intent i = new Intent();
        i.putExtra("newdetail", detail);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void setText(String text) {
        DescribEditText.setText(text);
    }

    @Override
    public void setDate(String date) {
        DateButton.setText(date);
    }

    @Override
    public void setTime(String time) {
        TimeButton.setText(time);
    }

    @Override
    public void setCategoryName(String name) {
        CategoryButton.setText(name);
    }

    @Override
    public void setMoney(String money) {
        SummText.setText(money);
    }

    @Override
    public void setLocation(String oldLocation){
        MyLocation.setText(oldLocation);
        LocationButton.setEnabled(false);
    }

    @Override
    public void loadPhoto(String uri) {
        mPhoto.setImageURI(Uri.parse((uri)));
        currentPhotoPath = uri;
        TakePhotoButton.setVisibility(View.GONE);
    }

    @Override
    public void takePhoto(Detail d) {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        APPLICATION_ID + ".provider",
                        photoFile);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public File createFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", new Locale("ru", "RU")).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera/");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        Log.d("FUCKCAMERA", "galleryAddPic: image add");
    }

    @Override
    public void takePhotoFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }


    @Override
    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(buildRequest(),locationCallback, null);
        }
        else ActivityCompat.requestPermissions(this, permission, 238);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                mPhoto.setImageURI(Uri.parse(currentPhotoPath));
                galleryAddPic();
                TakePhotoButton.setVisibility(View.GONE);
            } else if (requestCode == REQUEST_CATEGORY) {
                presenter.detailCategory((String) data.getSerializableExtra("category"));
            }
            else if (requestCode == REQUEST_GALLERY_PHOTO){
                currentPhotoPath = String.valueOf(data.getData());
                mPhoto.setImageURI(Uri.parse(currentPhotoPath));
            }
        }
    }
    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        initDateTime();
    };

    TimePickerDialog.OnTimeSetListener t = (view, hourOfDay, minute) -> {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        initDateTime();
    };

    private void checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(this, permission[2]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, permission[1]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_GRANTED){
            dialog = new PhotoDialog();
            dialog.show(getSupportFragmentManager(), "cameraAndGallery");
        }
        else ActivityCompat.requestPermissions(this, permission, 228);
    }

    private void checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this, permission[3]) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, permission[4]) == PackageManager.PERMISSION_GRANTED){
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                DialogFragment dialog = new GpsSettingsDialog();
                dialog.show(getSupportFragmentManager(), "gps_settings");
            }
            else presenter.geoOnClick();
        }
        else ActivityCompat.requestPermissions(this, permission, 238);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 228) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                PhotoDialog dialog = new PhotoDialog();
                dialog.show(getSupportFragmentManager(), "cameraAndGallery");
            }
        }
        else if (requestCode == 238){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                presenter.geoOnClick();
            }
        }
    }

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        AddressResultReceiver resultReceiver = new AddressResultReceiver(new Handler());
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, lastLocation);
        startService(intent);
    }

    @Override
    public void cameraOnClick() {
        presenter.cameraOnClick();
        dialog.dismiss();
    }

    @Override
    public void galleryClick() {
        presenter.gallaryOnClick();
        dialog.dismiss();
    }

    public class AddressResultReceiver extends ResultReceiver {
        String addressOutput;

        AddressResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultData == null) {
                return;
            }
            addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            if (addressOutput == null) {
                addressOutput = "";
            }
            MyLocation.setText(addressOutput);
            LocationButton.setVisibility(View.GONE);
        }
    }

    private LocationRequest buildRequest(){
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        return request;
    }

    @Override
    protected void onStop() {
        mClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mClient.connect();
    }
}
