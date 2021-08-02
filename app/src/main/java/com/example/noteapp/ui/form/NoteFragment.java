package com.example.noteapp.ui.form;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.noteapp.R;
import com.example.noteapp.databinding.FragmentNoteBinding;
import com.example.noteapp.model.NoteModel;
import com.example.noteapp.utisl.App;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.noteapp.ui.home.HomeFragment.UPDATE_MODEL_KEY;


public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;
    String time;
    private NoteModel model;
    private int count = 0;
    String keyradiobtn;
    private RadioGroup radioGroup;
    public static final int REQUESTKEYFORPERMISSION = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater, container, false);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                if (destination.getId() == R.id.noteFragment) {
                    binding.toolbarForm.setVisibility(View.VISIBLE);
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat monthsimpledate = new SimpleDateFormat("d MMMM");
                    SimpleDateFormat monthsimpletime = new SimpleDateFormat("HH:mm");
                    String month = monthsimpledate.format(cal.getTime());
                    String date = monthsimpletime.format(new Date());
                    binding.txtMonthNotefragment.setText(month);
                    binding.dateFromNote.setText(date);
                } else {
                    binding.toolbarForm.setVisibility(View.GONE);
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup = view.findViewById(R.id.radios);
        view.findViewById(R.id.btn_radio_grey).setOnClickListener(this::initRadio);
        view.findViewById(R.id.btn_radio_white).setOnClickListener(this::initRadio);
        view.findViewById(R.id.radio_btn_red).setOnClickListener(this::initRadio);
        userPermissions();
        editData();
        clickTxtDoneListener();
        radiobtn_getBackground();
        initRadio(view);
        click_audio_convertor();


    }

    private void radiobtn_getBackground() {
        binding.btnNotefragBlack.setOnClickListener(v -> {
            binding.btnRadioGrey.performClick();
            binding.radioBtnRed.setChecked(false);
            binding.btnRadioWhite.setChecked(false);
            keyradiobtn = "b";
            YoYo.with(Techniques.Shake)
                    .duration(150)
                    .repeat(3)
                    .playOn(binding.btnNotefragBlack);
        });
        binding.btnNoteWhite.setOnClickListener(v -> {
            binding.btnRadioWhite.performClick();
            binding.radioBtnRed.setChecked(false);
            binding.btnRadioGrey.setChecked(false);
            keyradiobtn = "w";
            YoYo.with(Techniques.Shake)
                    .duration(150)
                    .repeat(3)
                    .playOn(binding.btnNoteWhite);
        });
        binding.btnNoteRed.setOnClickListener(v -> {
            binding.radioBtnRed.performClick();
            binding.btnRadioWhite.setChecked(false);
            binding.btnRadioGrey.setChecked(false);
            keyradiobtn = "r";
            YoYo.with(Techniques.Shake)
                    .duration(150)
                    .repeat(3)
                    .playOn(binding.btnNoteRed);
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void initRadio(View view) {
        switch (view.getId()) {
            case R.id.btn_radio_grey:
            case R.id.btn_radio_white:
            case R.id.radio_btn_red:
                radioGroup.clearCheck();
                radioGroup.check(view.getId());
                break;
        }
    }
    private void click_audio_convertor() {
        SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireActivity());
        final Intent speehcReconizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        binding.audioConvertorVoicetext.setOnClickListener(v -> {
            if (count == 0) {
                binding.audioConvertorVoicetext.setImageResource(R.drawable.ic_baseline_keyboard_voice_24);
                speechRecognizer.startListening(speehcReconizerIntent);
                YoYo.with(Techniques.Shake)
                        .duration(100)
                        .repeat(4)
                        .playOn(binding.audioConvertorVoicetext);

                count = 1;
            } else {
                binding.audioConvertorVoicetext.setImageResource(R.drawable.ic_baseline_mic_off_24);
                speechRecognizer.stopListening();
                YoYo.with(Techniques.Hinge)
                        .duration(100)
                        .repeat(3)
                        .playOn(binding.audioConvertorVoicetext);
                count = 0;
            }

        });
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
                binding.etNoteFragment.setHint(R.string.ettitle_textListening);
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                speechRecognizer.stopListening();
            }

            @Override
            public void onError(int error) {
                binding.audioConvertorVoicetext.setImageResource(R.drawable.ic_baseline_mic_off_24);
                binding.etNoteFragment.setHint(R.string.enter_title);

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> data = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
                binding.etNoteFragment.setText(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
    }


    private void userPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, REQUESTKEYFORPERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTKEYFORPERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireActivity(),
                        R.string.permission_granted,
                        Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(requireActivity(),
                    R.string.permission_dein,
                    Toast.LENGTH_SHORT).show();


        }
    }

    private void clickTxtDoneListener() {

        binding.txtDoneNoteFragment.setOnClickListener(v -> {
            String title = binding.etNoteFragment.getText().toString();
            if (TextUtils.isEmpty(title)) {
                binding.etNoteFragment.setError(getString(R.string.seterror_text));
                YoYo.with(Techniques.Shake)
                        .duration(600)
                        .repeat(2)
                        .playOn(binding.etNoteFragment);
                return;
            }
            if (model == null) {
                SimpleDateFormat sdfTime = new SimpleDateFormat("d MMMM HH:mm");
                time = sdfTime.format(new Date());
                model = new NoteModel(title, time,keyradiobtn);
                App.getInstance().noteDao().insertNote(model);
            } else {
                model.setTxtTitle(title);
                model.setDate(time);
                model.setRadiobac(keyradiobtn);
                App.getInstance().noteDao().update(model);
            }
            close();
        });
//        binding.backToHomefragment.setOnClickListener(v -> {
//            close();
//        });
    }

    private void editData() {

        if (getArguments() != null) {
            model = (NoteModel) getArguments().getSerializable(UPDATE_MODEL_KEY);
            if (model != null) {
                binding.etNoteFragment.setText(model.getTxtTitle());
            }
        }
    }

    void getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MMMM:kk");
        Date currentTime = Calendar.getInstance().getTime();
        String times = dateFormat.format(currentTime);
        binding.dateFromNote.setText(times);
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();

    }
}