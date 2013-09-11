package edu.uncc.mad.hw2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText priceEditText;
	private RadioGroup discountRadioGroup;
	private TextView savedTextView;
	private TextView payTextView;
	private SeekBar customSeekBar;
	private TextView customDiscountDisplayerTextView;
	private Button exitButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	
		initializeWidgets();
		
		setupWidgetEventHandlers();
		
	}

	private float getPrintedPrice(){
		
		float printedPrice = 0.0f;
		
		String enteredText = priceEditText.getText().toString();
		
		if(enteredText.length() == 0){
			priceEditText.setError("Please enter the printed price.");
			return -1.0f;
		}
		
		try {
			printedPrice = Float.parseFloat(enteredText);
		}catch(Exception e){
			priceEditText.setError("Please enter a valid price.");
			return -1;
		}
		
		return printedPrice;
	}
	
	private float getDiscount() {
		int selectedDiscountId = discountRadioGroup.getCheckedRadioButtonId();
		
		if(selectedDiscountId == R.id.tenPercentRadio)
			return 10.0f;
		
		if(selectedDiscountId == R.id.twentyPercentRadio)
			return 20.0f;
		
		if(selectedDiscountId == R.id.fiftyPercentRadio)
			return 50.0f;
		
		if(selectedDiscountId == R.id.customRadio)
			return customSeekBar.getProgress();
		
		return -1;
	}
	
	private void setupWidgetEventHandlers() {
		
		priceEditText.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent keyEvt) {
				if(keyEvt.getAction() == KeyEvent.ACTION_UP){
					calculateAndUpdateDiscountValues(getPrintedPrice(), getDiscount());
				}
				
				return false;
			}
		});
		
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		customSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				//Nothing to do
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				//Nothing to do
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

				customDiscountDisplayerTextView.setText(progress + "%");

				if(discountRadioGroup.getCheckedRadioButtonId() != R.id.customRadio)
					return;
				
				calculateAndUpdateDiscountValues(getPrintedPrice(), progress);
				
			}
		});
		
		discountRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup grp, int selectedRadioID) {
				if(selectedRadioID == R.id.tenPercentRadio)
					calculateAndUpdateDiscountValues(getPrintedPrice(), 10.0f);	
				else if(selectedRadioID == R.id.twentyPercentRadio)
					calculateAndUpdateDiscountValues(getPrintedPrice(), 20.0f);
				else if(selectedRadioID == R.id.fiftyPercentRadio)
					calculateAndUpdateDiscountValues(getPrintedPrice(), 50.0f);
				else if(selectedRadioID == R.id.customRadio)
					calculateAndUpdateDiscountValues(getPrintedPrice(), customSeekBar.getProgress());
			}
		});
	}
	
	private void calculateAndUpdateDiscountValues(float printedPrice, float discount){
		
		if(printedPrice < 0.0f)
			return;
		
		//save some processing if discount is 0%
		if(discount == 0.0f){
			payTextView.setText(String.format("$ %.2f", printedPrice));
			savedTextView.setText(String.format("$ 0.0"));
			return;
		}
		
		float discountInDollars = printedPrice * (discount/100.0f);
		float priceAfterDiscount = printedPrice - discountInDollars;
		
		payTextView.setText(String.format("$ %.2f", priceAfterDiscount));
		savedTextView.setText(String.format("$ %.2f", discountInDollars));
	}

	private void initializeWidgets() {
		priceEditText = (EditText) findViewById(R.id.priceTextField);
		discountRadioGroup = (RadioGroup) findViewById(R.id.discountRadioGroup);
		savedTextView = (TextView) findViewById(R.id.savedTextField);
		payTextView = (TextView) findViewById(R.id.payTextField);
		customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
		exitButton = (Button) findViewById(R.id.exitButton);
		customDiscountDisplayerTextView = (TextView) findViewById(R.id.customDiscountDisplayTextField);
		
		//also initialize the seekbar.
		customSeekBar.setMax(100);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}