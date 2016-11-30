package in.ac.iifm.devadevan.mydbase;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TABLE_NAME="employee";
    private static final String COL_1="ID";
    private static final String COL_2="ename";
    private static final String COL_3="basic";
    private static final String COL_4="gradepay";

    EditText e1,e2,e3,e4;
    Button btnInsert,btnEdit,btnShow,btnDelete;
    MyDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MyDbHelper(this);
        String s="CREATE TABLE "+TABLE_NAME+"("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT , "+COL_2+" TEXT NOT NULL , "+COL_3+" INTEGER NOT NULL , "+COL_4+" INTEGER NOT NULL)";

        e1=(EditText) findViewById(R.id.editEmpid);
        e2=(EditText) findViewById(R.id.editEname);
        e3=(EditText) findViewById(R.id.editBasic);
        e4=(EditText) findViewById(R.id.editGradepay);

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnShow = (Button) findViewById(R.id.btnShow);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=e2.getText().toString();
                String b = e3.getText().toString();
                String g = e4.getText().toString();
                if(name.length()==0 || b.length()==0 || g.length()==0)
                    {
                        Toast.makeText(getBaseContext(),"Required information missing",Toast.LENGTH_LONG).show();
                    }
                else
                {
                    long id = db.insertData(name, Integer.parseInt(b), Integer.parseInt(g));
                    if (id == -1)
                        Toast.makeText(getBaseContext(), "Insertion Failed", Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(getBaseContext(), "Record Added Successfully with EmpID : " + id, Toast.LENGTH_LONG).show();
                        e2.setText("");
                        e3.setText("");
                        e4.setText("");
                    }
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eid = e1.getText().toString();
                String name=e2.getText().toString();
                String b = e3.getText().toString();
                String g = e4.getText().toString();
                if(eid.length() ==0 || name.length()==0 || b.length()==0 || g.length()==0)
                {
                    Toast.makeText(getBaseContext(),"Required information missing",Toast.LENGTH_LONG).show();
                }
                else
                {
                    int id = db.updateData(eid, name, Integer.parseInt(b), Integer.parseInt(g));
                    //Toast.makeText(getBaseContext(), "Return value : "+id, Toast.LENGTH_LONG).show();
                    if (id == 0)
                        Toast.makeText(getBaseContext(), "Update Failed", Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(getBaseContext(), "Record Updated Successfully " + id, Toast.LENGTH_LONG).show();
                        e1.setText("");
                        e2.setText("");
                        e3.setText("");
                        e4.setText("");
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eid = e1.getText().toString();
                if (eid.length() == 0)
                    Toast.makeText(getBaseContext(), "ID is required", Toast.LENGTH_LONG).show();
                else {
                    int rs = db.deleteData(eid);
                    if (rs == 0)
                        Toast.makeText(getBaseContext(), "Deletion Failed!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getBaseContext(), "Record deleted Successfully!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor rs = db.show_data();
                if(rs.getCount() == 0)
                    showMessage("Not found","No records found!");
                else
                {
                    StringBuffer buffer=new StringBuffer();
                    buffer.append("<h2 style='text-align:center;color:green'>Employee Records</h2>");
                    buffer.append("<table width='100%' border='1'><tr><th width='15%'>Emp ID</th><th width='45%'>Emp Name</th><th width='20%'>Basic</th><th width='20%'>Gross Pay</th></tr>");
                    while(rs.moveToNext())
                    {
                        buffer.append("<tr><td>"+rs.getString(0)+"</td>");
                        buffer.append("<td>"+rs.getString(1)+"</td>");
                        buffer.append("<td style='text-align:right'>"+rs.getString(2)+"</td>");
                        buffer.append("<td style='text-align:right'>"+rs.getString(3)+"</td></tr>");
                    }
                    buffer.append("</table>");
                    Intent i = new Intent(getBaseContext(),ResultActivity.class);
                    i.putExtra("RECORDS",buffer.toString());
                    startActivity(i);
                }
            }
        });
    }

    public void showMessage(String title, String data)
    {
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setTitle(title);
        b.setMessage(data);
        b.show();
    }
}
