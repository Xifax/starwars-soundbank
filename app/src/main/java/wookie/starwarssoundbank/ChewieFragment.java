package wookie.starwarssoundbank;


import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;


/**
 * A Chewie sounds fragment!
 */
public class ChewieFragment extends Fragment {

    /* Properties */
    private Map<String, Integer> sounds = new HashMap();
    private MediaPlayer player;

    /* Views */
    @Bind(R.id.buttons) LinearLayout buttons;

    /* Audio resources */
    private final String[] labels = new String[] {
        "Wah! snort",
        "Rhawk-Arghh!",
        "Ahhowwwahow",
        "Woh Arg-oh-oh",
        "Ahhhgh!",
        "Rrrrooaarrgghh!",
        "Wha!",
        "Whoa!",
        "Arrrrghh!",
        "Rwahh-oh-oh",
        "Rgrah rha",
        "Ahhhh! Argh",
        "Arhhhh-rh",
        "Wooouah!",
        "Rrrrugh!",
    };
    private final Integer[] resources = new Integer[] {
        R.raw.chwbc_01,
        R.raw.chwbc_02,
        R.raw.chwbc_03,
        R.raw.chwbc_04,
        R.raw.chwbc_05,
        R.raw.chwbc_06,
        R.raw.chwbc_07,
        R.raw.chwbc_08,
        R.raw.chwbc_09,
        R.raw.chwbc_10,
        R.raw.chwbc_11,
        R.raw.chwbc_12,
        R.raw.chwbc_13,
        R.raw.chwbc_14,
        R.raw.chwbc_15,
    };

    /**
     * Inject all the buttons!
     */
    public static ChewieFragment newInstance() {
        ChewieFragment fragment = new ChewieFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public ChewieFragment() {
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
