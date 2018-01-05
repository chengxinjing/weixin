package com.weixin.web.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.weixin.web.constance.MyConst;
@Component
public final class WeixinApiUtil {

	@Autowired
	private  AccessTokenUtil accessTokenUtil;
	//创建menu接口
	private static final String CREAT_MENU = ResourceBundle.getBundle(MyConst.BASE_NAME).getString("creat_menu_api");
	//文件上传接口
	private static final String FILE_UPLOAD = ResourceBundle.getBundle(MyConst.BASE_NAME).getString("file_upload_api");
	//文件下载接口
	private static final String FILE_DOWLOAD = ResourceBundle.getBundle(MyConst.BASE_NAME).getString("file_dowload_api");
	//menu.txt文件 默认地址
	private static String MENU_PATH = ResourceBundle.getBundle(MyConst.BASE_NAME).getString("menu_path");

	// 创建自定菜单栏
	public  String createMenu() throws IOException {
		InputStream inputStream = WeixinApiUtil.class.getResourceAsStream(MENU_PATH);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		String text;
		StringBuffer buffer = new StringBuffer();
		while ((text = bufferedReader.readLine()) != null) {
			buffer.append(text);
		}
		inputStream.close();
		bufferedReader.close();
		JSONObject jsonObject = JSON.parseObject(buffer.toString());
		String token =  getToken();
		String api = getCreatMenuUrl(token);
		String result = NetWorkHelper.sendHttpResponse(api, jsonObject.toJSONString(), "POST");
		System.out.println(result);
		return result;
	}
	/**
	 * 设置菜单地址
	 * @param menuPath
	 */
	public static void setMENU_PATH(String menuPath) {
		MENU_PATH = menuPath;
	}
	/**
	 * 获得token值
	 * @return
	 */
	public  String getToken() {
		return accessTokenUtil.getAccessTokenFromCache("access_token").getToken();
	}
	/**
	 * 创建自定义菜单的url
	 * @return
	 */
	private  String getCreatMenuUrl(String token) {
		return String.format(CREAT_MENU, token);
	}
	
	
	/**
     * 上传素材
     * @param filePath 媒体文件路径(绝对路径)
     * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @return
     */
    public  JSONObject uploadMedia(String filePath,String type){
        File f = new File(filePath); // 获取本地文件
        String token = getToken();
        JSONObject jsonObject = uploadMedia(f, token, type);
       System.out.println(jsonObject.toJSONString());
        return jsonObject;
    }
    
	 /**
     * 微信服务器素材上传
     *
     * @param file  表单名称media
     * @param token access_token
     * @param type  type只支持四种类型素材(video/image/voice/thumb)
     */
    public static JSONObject uploadMedia(File file, String token, String type) {
        if (file == null || token == null || type == null) {
            return null;
        }

        if (!file.exists()) {
            System.out.println("上传文件不存在,请检查!");
            return null;
        }

        String url = FILE_UPLOAD;
        JSONObject jsonObject = null;
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Connection", "Keep-Alive");
        post.setRequestHeader("Cache-Control", "no-cache");
        FilePart media;
        HttpClient httpClient = new HttpClient();
        //信任任何类型的证书
        Protocol myhttps = new Protocol("https", new SSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);

        try {
            media = new FilePart("media", file);
            Part[] parts = new Part[]{new StringPart("access_token", token),
                    new StringPart("type", type), media};
            MultipartRequestEntity entity = new MultipartRequestEntity(parts,
                    post.getParams());
            post.setRequestEntity(entity);
            int status = httpClient.executeMethod(post);
            if (status == HttpStatus.SC_OK) {
                String text = post.getResponseBodyAsString();
                jsonObject = JSONObject.parseObject(text);
            } else {
                System.out.println("upload Media failure status is:" + status);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    /**
     * 多媒体下载接口
     *
     * @param fileName 素材存储文件路径
     * @param token    认证token
     * @param mediaId  素材ID（对应上传后获取到的ID）
     * @return 素材文件
     * @comment 不支持视频文件的下载
     */
    public  File downloadMedia(String fileName, String token,
                                     String mediaId) {
        String url = getDownloadUrl(token, mediaId);
        return httpRequestToFile(fileName, url, "GET", null);
    }
    
    private static String getDownloadUrl(String token, String mediaId) {
		
		return String.format(FILE_DOWLOAD, token,mediaId);
	}
	
    /**
     * 多媒体下载接口
     *
     * @param fileName 素材存储文件路径
     * @param mediaId  素材ID（对应上传后获取到的ID）
     * @return 素材文件
     * @comment 不支持视频文件的下载
     */
    public  File downloadMedia(String fileName, String mediaId) {
        return downloadMedia(fileName,getToken(),mediaId);
    }
    /**
     * 以http方式发送请求,并将请求响应内容输出到文件
     *
     * @param path   请求路径
     * @param method 请求方法
     * @param body   请求数据
     * @return 返回响应的存储到文件
     */
    public static File httpRequestToFile(String fileName, String path, String method, String body) {
        if (fileName == null || path == null || method == null) {
            return null;
        }

        File file = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream fileOut = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }

            inputStream = conn.getInputStream();
            if (inputStream != null) {
                file = new File(fileName);
            } else {
                return file;
            }

            //写入到文件
            fileOut = new FileOutputStream(file);
            if (fileOut != null) {
                int c = inputStream.read();
                while (c != -1) {
                    fileOut.write(c);
                    c = inputStream.read();
                }
            }
        } catch (Exception e) {
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

            /*
             * 必须关闭文件流
             * 否则JDK运行时，文件被占用其他进程无法访问
             */
            try {
                inputStream.close();
                fileOut.close();
            } catch (IOException execption) {
            }
        }
        return file;
    }

}
