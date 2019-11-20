package com.example.demo.utils.common.utils;

//import javazoom.jl.decoder.Bitstream;
//import javazoom.jl.decoder.Header;

public class AudioUtil {
//
//	@SuppressWarnings("rawtypes")
//	private static int getAudioLongLocation(String mp3File) {
//		File file = new File(mp3File);
//		int total = 0;
//		try {
//			AudioFileFormat aff = AudioSystem.getAudioFileFormat(file);
//			Map props = aff.properties();
//			if (props.containsKey("duration"))
//				total = (int) Math.round((((Long) props.get("duration")).longValue()) / 1000);
//			// System.out.println(total);
//		} catch (UnsupportedAudioFileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return total;
//	}
//
//	private static int getAudioLongUrl(String url) {
//		try {
//			URL urlfile = new URL(url);
//			// URL urlfile = new
//			// URL("http://www.greenworm.com/ising/song02/135_158.mp3");
//			// File file = new File("C:\\music\\test2.mp3");
//			// URL urlfile = file.toURI().toURL();
//			URLConnection con = urlfile.openConnection();
//			int b = con.getContentLength();// 得到音乐文件的总长度
//			BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
//			Bitstream bt = new Bitstream(bis);
//			Header h = bt.readFrame();
//			int time = (int) h.total_ms(b);
////			System.out.println(time / 1000);
//			return time / 1000;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
//
//	/** 音频文件时长 */
//	public static int getAudioLong(String file) {
//		if (file.indexOf("http") >= 0 || file.contentEquals("ftp://")) {
//			return getAudioLongUrl(file);
//		} else {
//			return getAudioLongLocation(file);
//		}
//	}
}
