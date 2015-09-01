package wookie.starwarssoundbank;


import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;


/**
 * Pew-pew! Pew!
 */
public class BlasterFragment extends Fragment {

    /* Properties */
    private Map<String, Integer> sounds = new HashMap<>();
    private MediaPlayer player;

    /* Views */
    @Bind(R.id.buttons) LinearLayout buttons;

    /* Audio resources */
    private final String[] labels = new String[] {
        "Battledroid blaster",
        "Boba Fett's blaster",
        "Chewie's bowcaster",
        "Clone trooper blaster",
        "Droideka blaster",
        "Han Solo's blaster",
        "Jango Fett's blaster",
        "Jawa ion blaster",
        "Stormtrooper blaster",
        "Stormtrooper stun blast",
    };
    private final Integer[] resources = new Integer[] {
        R.raw.blaster_01,
        R.raw.blaster_02,
        R.raw.blaster_03,
        R.raw.blaster_04,
        R.raw.blaster_05,
        R.raw.blaster_06,
        R.raw.blaster_07,
        R.raw.blaster_08,
        R.raw.blaster_09,
        R.raw.blaster_10,
    };

    /**
     * Inject all the buttons!
     */
    public static BlasterFragment newInstance() {
        BlasterFragment fragment = new BlasterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public BlasterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize sounds
        int index = 0;
        for(String label : labels) {
            sounds.put(label, resources[index]);
            index++;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chewie, container, false);
        ButterKnife.bind(this, view);

        // Iteratively create buttons
        TypedArray colors = getResources().obtainTypedArray(R.array.colors);
        for(final String label : labels) {
            FButton audioButton = new FButton(getActivity());

            // Play corresponding sound on click
            audioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playSound(sounds.get(label));
                }
            });

            // Set random color and text label
            int randomIndex = new Random().nextInt(colors.length());
            audioButton.setButtonColor(Color.parseColor(colors.getString(randomIndex)));
            audioButton.setTextColor(Color.WHITE);
            audioButton.setText(label);

            // Setup layout
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            int margin = getResources().getDimensionPixelOffset(R.dimen.button_margin);
            params.setMargins(margin, margin, margin, 0);
            audioButton.setLayoutParams(params);

            // Append to the end of layout
            buttons.addView(audioButton);
        }

        return view;
    }

    /**
     * Play sound (from RAWs) with specified id
     */
    private void playSound(int id) {
        if(player != null) {
            player.release();
            player = null;
        }

        player = MediaPlayer.create(getActivity(), id);
        player.start();
    }


}
