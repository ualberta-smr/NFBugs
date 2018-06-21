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
    public void pattern()
        {
            if ( !execute )
            {
                execute = true;
                initialise();
                
                DataSource ds = (DataSource) cbxType.getSelectedItem();                
                
              
                ds.closeSource();
        }
    }
