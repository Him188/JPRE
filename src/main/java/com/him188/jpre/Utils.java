package com.him188.jpre;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Utils {
	public static void writeFile(String fileName, String content) throws IOException {
		writeFile(fileName, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
	}

	public static void writeFile(String fileName, InputStream content) throws IOException {
		writeFile(new File(fileName), content);
	}

	public static void writeFile(File file, String content) throws IOException {
		writeFile(file, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
	}

	public static void writeFile(File file, InputStream content) throws IOException {
		if (content == null) {
			throw new IllegalArgumentException("content must not be null");
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream stream = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = content.read(buffer)) != -1) {
			stream.write(buffer, 0, length);
		}
		stream.close();
		content.close();
	}

	public static String readFile(File file) throws IOException {
		if (!file.exists() || file.isDirectory()) {
			throw new FileNotFoundException();
		}
		return readFile(new FileInputStream(file));
	}

	public static String readFile(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists() || file.isDirectory()) {
			throw new FileNotFoundException();
		}
		return readFile(new FileInputStream(file));
	}

	public static String readFile(InputStream inputStream) throws IOException {
		return readFile(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
	}

	private static String readFile(Reader reader) throws IOException {
		BufferedReader br = new BufferedReader(reader);
		String temp;
		StringBuilder stringBuilder = new StringBuilder();
		temp = br.readLine();
		while (temp != null) {
			if (stringBuilder.length() != 0) {
				stringBuilder.append("\n");
			}
			stringBuilder.append(temp);
			temp = br.readLine();
		}
		br.close();
		reader.close();
		return stringBuilder.toString();
	}


	public static byte[] base64decode(String string) {
		if (string == null || string.isEmpty()) {
			return new byte[0];
		}
		try {
			return new BASE64Decoder().decodeBuffer(string);
		} catch (IOException e) {
			return new byte[0];
		}
	}

	public static String md5Encode(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		return base64en.encode(md5.digest(str.getBytes("utf-8")));
	}
}
