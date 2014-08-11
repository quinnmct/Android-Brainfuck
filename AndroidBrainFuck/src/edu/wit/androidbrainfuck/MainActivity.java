package edu.wit.androidbrainfuck;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public EditText bfCode;
	public Button helloWorldButton, runButton;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        bfCode = (EditText) findViewById(R.id.editText1);
        
        helloWorldButton = (Button) findViewById(R.id.helloWorldButton);
        helloWorldButton.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v) {
        		
        		//load Hello World code into the EditText object
        		bfCode.setText("++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.");
        	}
        });
        
        runButton = (Button) findViewById(R.id.runButton);
        runButton.setOnClickListener(new View.OnClickListener(){
        	String result = "";
        	
        	public void onClick(View v) {
        		
        		try {
					Brainfuck bf = new Brainfuck(30000);
					result = bf.bfInterpret(bfCode.getText().toString());
        		} catch (Exception e) {
					e.printStackTrace();
				}
        		
        		
        		
        		Intent i = new Intent(getApplicationContext(), DisplayCodeActivity.class);
            	i.putExtra("result", result);
            	i.putExtra("input", bfCode.getText().toString());
            	startActivity(i);
            	
        	}
        });
        

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
