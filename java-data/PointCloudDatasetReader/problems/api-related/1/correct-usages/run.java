package nz.ac.aut.SentienceLab.PointCloudDatasetReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;

private class Converter implements Runnable
    {
    public void run()
        {
            if ( !execute )
            {
                execute = true;
                initialise();
                
                DataSource ds = (DataSource) cbxType.getSelectedItem();
                List<PointData> pointList = new ArrayList<>();
                if ( ds.openSource(new File(txtSource.getText())) )
                {
                    PointData p = null;    
                    do
                    {
                        try
                        {
                            p = ds.readSource();
                            if ( p != null)
                            {
                                pointList.add(p);
                                prgConverting.setString("" + pointList.size());
                            }
                        }
                        catch (IOException | ParseException e)
                        {
                            System.err.println(e);
                            execute = false;
                        }
                    }
                    while ( execute && (p != null) );
                }
                else
                {
                    execute = false;
                }
                ds.closeSource();
        }
    }
