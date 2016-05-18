package com.flatstack.socialnetworks;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Ilya Eremin on 20.04.2016.
 */
public class TwitterComposer {
    private static final String MIME_TYPE_PLAIN_TEXT = "text/plain";
    private static final String MIME_TYPE_JPEG       = "image/jpeg";
    private static final String TWITTER_PACKAGE_NAME = "com.twitter.android";
    private static final String WEB_INTENT           = "https://twitter.com/intent/tweet?text=%s&url=%s";

    public TwitterComposer() {
    }

    public String getVersion() {
        return "1.0.2.97";
    }

    public static class Builder {
        private final Context context;
        private       String  text;
        private       URL     url;
        private       Uri     imageUri;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            } else {
                this.context = context;
            }
        }

        public TwitterComposer.Builder text(String text) {
            if (text == null) {
                throw new IllegalArgumentException("text must not be null.");
            } else if (this.text != null) {
                throw new IllegalStateException("text already set.");
            } else {
                this.text = text;
                return this;
            }
        }

        public TwitterComposer.Builder url(URL url) {
            if (url == null) {
                throw new IllegalArgumentException("url must not be null.");
            } else if (this.url != null) {
                throw new IllegalStateException("url already set.");
            } else {
                this.url = url;
                return this;
            }
        }

        public TwitterComposer.Builder image(Uri imageUri) {
            if (imageUri == null) {
                throw new IllegalArgumentException("imageUri must not be null.");
            } else if (this.imageUri != null) {
                throw new IllegalStateException("imageUri already set.");
            } else {
                this.imageUri = imageUri;
                return this;
            }
        }

        public Intent createIntent() {
            Intent intent = this.createTwitterIntent();
            if (intent == null) {
                intent = this.createWebIntent();
            }

            return intent;
        }

        Intent createTwitterIntent() {
            Intent intent = new Intent("android.intent.action.SEND");
            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(this.text)) {
                builder.append(this.text);
            }

            if (this.url != null) {
                if (builder.length() > 0) {
                    builder.append(' ');
                }

                builder.append(this.url.toString());
            }

            intent.putExtra("android.intent.extra.TEXT", builder.toString());
            intent.setType(MIME_TYPE_PLAIN_TEXT);
            if (this.imageUri != null) {
                intent.putExtra("android.intent.extra.STREAM", this.imageUri);
                intent.setType(MIME_TYPE_JPEG);
            }

            PackageManager packManager = this.context.getPackageManager();
            List resolvedInfoList = packManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            Iterator i$ = resolvedInfoList.iterator();

            ResolveInfo resolveInfo;
            do {
                if (!i$.hasNext()) {
                    return null;
                }

                resolveInfo = (ResolveInfo) i$.next();
            } while (!resolveInfo.activityInfo.packageName.startsWith(TWITTER_PACKAGE_NAME));

            intent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            return intent;
        }

        Intent createWebIntent() {
            String url = this.url == null ? "" : this.url.toString();
            String tweetUrl = String.format(WEB_INTENT, UrlUtils.urlEncode(this.text), UrlUtils.urlEncode(url));
            return new Intent("android.intent.action.VIEW", Uri.parse(tweetUrl));
        }

        public void show() {
            Intent intent = this.createIntent();
            this.context.startActivity(intent);
        }
    }

    private static class UrlUtils {
        public static final String UTF8 = "UTF8";

        private UrlUtils() {
        }

        public static TreeMap<String, String> getQueryParams(URI uri, boolean decode) {
            return getQueryParams(uri.getRawQuery(), decode);
        }

        public static TreeMap<String, String> getQueryParams(String paramsString, boolean decode) {
            TreeMap params = new TreeMap();
            if(paramsString == null) {
                return params;
            } else {
                String[] arr$ = paramsString.split("&");

                for (String nameValuePairString : arr$) {
                    String[] nameValuePair = nameValuePairString.split("=");
                    if (nameValuePair.length == 2) {
                        if (decode) {
                            params.put(urlDecode(nameValuePair[0]), urlDecode(nameValuePair[1]));
                        } else {
                            params.put(nameValuePair[0], nameValuePair[1]);
                        }
                    } else if (!TextUtils.isEmpty(nameValuePair[0])) {
                        if (decode) {
                            params.put(urlDecode(nameValuePair[0]), "");
                        } else {
                            params.put(nameValuePair[0], "");
                        }
                    }
                }
                return params;
            }
        }

        public static String urlEncode(String s) {
            if(s == null) {
                return "";
            } else {
                try {
                    return URLEncoder.encode(s, UTF8);
                } catch (UnsupportedEncodingException var2) {
                    throw new RuntimeException(var2.getMessage(), var2);
                }
            }
        }

        public static String urlDecode(String s) {
            if(s == null) {
                return "";
            } else {
                try {
                    return URLDecoder.decode(s, UTF8);
                } catch (UnsupportedEncodingException var2) {
                    throw new RuntimeException(var2.getMessage(), var2);
                }
            }
        }

        public static String percentEncode(String s) {
            if(s == null) {
                return "";
            } else {
                StringBuilder sb = new StringBuilder();
                String encoded = urlEncode(s);
                int encodedLength = encoded.length();

                for(int i = 0; i < encodedLength; ++i) {
                    char c = encoded.charAt(i);
                    if(c == 42) {
                        sb.append("%2A");
                    } else if(c == 43) {
                        sb.append("%20");
                    } else if(c == 37 && i + 2 < encodedLength && encoded.charAt(i + 1) == 55 && encoded.charAt(i + 2) == 69) {
                        sb.append('~');
                        i += 2;
                    } else {
                        sb.append(c);
                    }
                }

                return sb.toString();
            }
        }
    }
}
