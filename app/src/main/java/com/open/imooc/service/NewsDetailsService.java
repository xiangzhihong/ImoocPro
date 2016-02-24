package com.open.imooc.service;

import java.io.IOException;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class NewsDetailsService {
	public static String getNewsDetails(String url, String news_title) {
		Document document = null;
		String data = "<body>" +
				"<center><h2 style='font-size:16px;'>" + news_title + "</h2></center>";
		data = data + "<p align='left' style='margin-left:10px'>" 
				+ "<span style='font-size:10px;'>" 
				+ "</span>"
				+ "</p>";
		data = data + "<hr size='1' />";
		try {
			document = Jsoup.connect(url).timeout(9000).get();
			Element element = null;
			if (TextUtils.isEmpty(url)) {
				data = "";
				element = document.getElementById("memberArea");
			} else {
				element = document.getElementById("artibody");
			}
			if (element != null) {
				data = data + element.toString();
			}
			data = data + "</body>";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
