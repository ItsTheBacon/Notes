package com.example.noteapp.ui.form;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.util.Log;
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


public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;
    NoteModel model;
    private int count = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPermissions();
        editData();
        clickTxtDoneListener();
        click_audio_convertor();


    }

    private void click_audio_convertor() {
        SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireActivity());
        final Intent speehcReconizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        binding.audioConvertorVoicetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    binding.audioConvertorVoicetext.setImageResource(R.drawable.ic_baseline_keyboard_voice_24);
                    speechRecognizer.startListening(speehcReconizerIntent);
                    YoYo.with(Techniques.FadeInDown)
                            .duration(2000)
                            .repeat(2)
                            .playOn(binding.audioConvertorVoicetext);

                    count = 1;
                } else {
                    binding.audioConvertorVoicetext.setImageResource(R.drawable.ic_baseline_mic_off_24);
                    speechRecognizer.stopListening();

                    count = 0;
                }

            }

        });
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
                binding.etNoteFragment.setHint("Listening...");

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

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

    private void radio_buttonchangeBackground() {
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_grey:


                }
            }
        });

    }

    private void userPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireActivity(), "Permissiom Granted", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(requireActivity(), "Permissiom dein", Toast.LENGTH_SHORT).show();


        }
    }

    private void clickTxtDoneListener() {

        binding.txtDoneNoteFragment.setOnClickListener(v -> {
            String title = binding.etNoteFragment.getText().toString();
            if (TextUtils.isEmpty(title)) {
                binding.etNoteFragment.setError("Input Title");
                YoYo.with(Techniques.Shake)
                        .duration(1000)
                        .repeat(3)
                        .playOn(binding.etNoteFragment);
                return;
            }
            if (model == null) {
                model = new NoteModel(title);
                App.getInstance().noteDao().insertNote(model);
                Log.e("TAG", "clickTxtDoneListener: " + model.getTxtTitle());


            } else {
                model.setTxtTitle(title);
                App.getInstance().noteDao().update(model);
            }
            close();
        });
        binding.backToHomefragment.setOnClickListener(v -> {
            close();
        });
    }

    private void editData() {

        if (getArguments() != null) {
            model = (NoteModel) getArguments().getSerializable("mod");
            if (model != null) {
                binding.etNoteFragment.setText(model.getTxtTitle());
            }
        }
    }

    void getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MMMM:kk");
        Date currentTime = Calendar.getInstance().getTime();
        String times = dateFormat.format(currentTime);
        binding.dateFromNotes.setText(times);
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();

    }
}