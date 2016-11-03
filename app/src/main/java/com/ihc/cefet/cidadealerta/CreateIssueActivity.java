package com.ihc.cefet.cidadealerta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by izabellamelendezconigliaro on 26/09/16.
 */
public class CreateIssueActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.image_text)
    TextView imageText;
    @Bind(R.id.category_tv)
    MaterialEditText categoryTv;
    @Bind(R.id.address_tv)
    MaterialEditText addressTv;
    @Bind(R.id.description_tv)
    MaterialEditText descriptionTv;
    @Bind(R.id.anonymous)
    TextView anonymous;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.delete_image)
    ImageView deleteImage;
    @Bind(R.id.places_autocomplete)
    PlacesAutocompleteTextView placesAutocomplete;

    private boolean isAnonymous = false;
    private int selection = 0;
    private boolean hasPic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_issue);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        configActionBar("Crie Ocorrência", true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        placesAutocomplete.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        addressTv.setText(place.description);
                        placesAutocomplete.setText(place.description);

                        descriptionTv.requestFocus();
                    }
                }
        );

        placesAutocomplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addressTv.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_issue, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            DialogUtils.showBasic(this, "Deseja descartar a ocorrência?", "As informações inseridas não poderão ser recuperadas" +
                    "em caso de descarte", "Descartar", "Cancelar", true, new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    finish();
                }

                @Override
                public void onNegative(MaterialDialog dialog) {
                    super.onNegative(dialog);
                    dialog.dismiss();
                }
            });
            return true;
        } else if (id == R.id.help) {
            HelpDialog.show(getSupportFragmentManager(), CreateIssueActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.image, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image:
                DialogUtils.showBasic(this, R.string.choose_pic_type, R.string.camera, R.string.gallery, new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        ImageUtils.checkPermissionAndStartCamera(CreateIssueActivity.this);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        ImageUtils.pickPhotoFromGallery(CreateIssueActivity.this);
                    }

                });
                break;
            case R.id.btn_login:

                if (TextUtils.isEmpty(categoryTv.getText().toString())) {
                    categoryTv.setError("Selecione uma categoria");
                    YoYo.with(Techniques.Shake)
                            .duration(500)
                            .playOn(categoryTv);
                } else if (TextUtils.isEmpty(addressTv.getText().toString())) {
                    addressTv.setError("Insira o endereço do ocorrido");
                    YoYo.with(Techniques.Shake)
                            .duration(500)
                            .playOn(addressTv);
                } else if (TextUtils.isEmpty(descriptionTv.getText().toString()) && !hasPic) {
                    descriptionTv.setError("Insira uma descrição ou adicione uma foto");
                    YoYo.with(Techniques.Shake)
                            .duration(500)
                            .playOn(descriptionTv);
                } else {

                    if(AndroidUtils.isNetworkAvailable(this)) {
                        btnLogin.setVisibility(View.GONE);
                        DialogUtils.showBasic(this, "Deseja publicar a ocorrência?", "Após publicação, você não será capaz de editar sua ocorrência." +
                                "Entretanto, você ainda será capaz de deleta-la enquanto estiver com o status aberto.", "Publicar", "Cancelar", true, new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                finish();
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        CreateIssueDialog.show(getSupportFragmentManager(), CreateIssueActivity.this);
                                    }
                                }, 1000);
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                                dialog.dismiss();
                                btnLogin.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        AndroidUtils.showSnackBarOk(this, btnLogin, R.string.internet_connection, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                }

                break;
        }
    }

    @OnClick(R.id.anonymous)
    public void onClick() {
        isAnonymous = !isAnonymous;
        anonymous.setSelected(isAnonymous);
    }

    @OnTouch(R.id.address_tv)
    boolean mapTouch(View v, MotionEvent event) {

        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (addressTv.getRight() - addressTv.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                openActivity(MapsActivity.class);

                return false;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case Constants.REQUEST_CAMERA:
                ImageUtils.launchCrop(data.getData(), this);
                break;

            case Constants.PICK_PHOTO_CODE:
                ImageUtils.launchCrop(data.getData(), this);
                break;

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:

                //addPicture.setVisibility(View.GONE);

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                Glide.with(this)
                        .load(result.getUri().getPath())
                        .into(image);

                hasPic = true;

                imageText.setVisibility(View.GONE);
                deleteImage.setVisibility(View.VISIBLE);

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.delete_image)
    public void deleteImage() {
        imageText.setVisibility(View.VISIBLE);
        deleteImage.setVisibility(View.GONE);

        Glide.with(this)
                .load(R.color.background)
                .into(image);

        hasPic = false;
    }

    @OnClick(R.id.category_tv)
    public void selectCategory() {
        final List<String> categories = Arrays.asList(getResources().getStringArray(R.array.categories));

        DialogUtils.showSingleChoice(this, "Selecione uma categoria", categories, selection, new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                selection = which;
                categoryTv.setText(categories.get(selection));
                return false;
            }
        });

    }
}
