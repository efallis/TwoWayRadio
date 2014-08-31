package com.szu.twowayradio.utils;


import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class RecordHelper {
	
	private RecordListener recordListener=null;
   

	private int audioSource = MediaRecorder.AudioSource.MIC;
    private static int sampleRateInHz = 8000;
	private static int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private static int audioFormat = AudioFormat.ENCODING_PCM_16BIT; 

    private int bufferSizeInBytes ; 
    int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
    int BytesPerElement = 2; 
    
    public int getBufferSizeInBytes() {
		return bufferSizeInBytes;
	}
	private boolean isRecord = false;
    
    public boolean isRecord() {
		return isRecord;
	}
    public void setRecordListener(RecordListener recordListener) {
		this.recordListener = recordListener;
	}
	private AudioRecord audioRecord;  
	
    public void init()
    {
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,  
               channelConfig, audioFormat);  
        Log.d("RecordHelper init", ""+bufferSizeInBytes);
        audioRecord = new AudioRecord(audioSource, sampleRateInHz,  
                channelConfig, audioFormat, bufferSizeInBytes);  
    }
    public void startRecord()
    {
    	audioRecord.startRecording();
    	isRecord=true;
    	if(recordListener!=null)
    	{
    		recordListener.recording(audioRecord);
    	}
    }
    public void stopRecord()
    {
        if(null!=audioRecord)
        {
        	isRecord=false;
        	audioRecord.stop();
        	audioRecord.release();
        	audioRecord=null;
        }
    }

    public static interface RecordListener {

        void recording(AudioRecord record);
    }
}