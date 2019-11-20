package com.example.demo.utils.framework.redis;


import com.example.demo.utils.common.enums.DynamicEnum;
import com.example.demo.utils.common.utils.ConfigHelper;
import com.example.demo.utils.common.utils.FunctionUtil;

public class RedisKey {

	public static String getTokenKey(String token) {
		return "token_" + token;
	}

	private static String test = FunctionUtil.ParseString(ConfigHelper.Config.getPropertie("test"));

	static {
		String str = FunctionUtil.ParseString(ConfigHelper.Config.getPropertie("test"));
		if ("true".equals(str)) {
			test = "test_";
		} else if ("false".equals(str)) {
			test = "";
		} else {
			test = str;
		}
	}

	public enum H_Name {
		/** */
		WEB_APP_CONFIG__PUSHSTATUS("web_app_config", "pushStatus"),
		/** */
		WEB_APP_CONFIG__PUSH_TYPE("web_app_config", "pushType"),
		/** 职业 */
		DICTIONARIES__JOB("dictionaries", "job"),
		/** 省 */
		DICTIONARIES__PROVINCE("dictionaries", "province"),
		/** 市 */
		DICTIONARIES__CITY("dictionaries", "city");

		/** */
		private static H_Name TOKEN(String token) {
			return updateOrAddEnum("TOKEN", "token", token);
		}

		private String hName;
		private String key;

		private static H_Name updateOrAddEnum(String enumKey, String hNameStr, String keyStr) {
			H_Name s = null;
			DynamicEnum.updateOrAddEnum(H_Name.class, enumKey, new Class[] { String.class, String.class },
					new Object[] { hNameStr, keyStr });
			s = H_Name.valueOf(enumKey);
			return s;
		}

		private H_Name(String hName, String key) {
			this.hName = hName;
			this.key = key;
		}

		public String getHName() {
			return test + this.hName + "_hash";
		}

		public String getKey() {
			return this.key;
		}
	}

	public enum Key {
		/** */
		MFAPP_VERSION_NEW("mfapp_version_new");

		/** token */
		public static String Token(String tokenStr) {
			String enumKey = "TOKEN";
			String enumValue = "token_" + tokenStr;
//			return updateOrAddEnum(enumKey, enumValue);
			return test + enumValue;
		}

		/** 苹果支付 */
		public static String APP_VERSION(String version) {
			String enumKey = "APP_VERSION";
			String enumValue = "app_version_" + version;
//			return updateOrAddEnum(enumKey, enumValue);
			 return test + enumValue;
		}

		/** 关注 */
		public static String ATTENTION_MAP(String memberId) {
//			return updateOrAddEnum("ATTENTION_MAP", "attentionMap" + memberId);
			 return test + "attentionMap" + memberId;
		}

		/** 最近来访 */
		public static String VISIT_MAP(String memberId) {
//			return updateOrAddEnum("VISIT_MAP", "visitMap" + memberId);
			 return test + "visitMap" + memberId;
		}

		/**  对虚拟用户的查看权限 */
		public static String Vip(String memberId,String targetId){
			return test + "vip_" + memberId + "_to_" + targetId;
		}

		private static Key updateOrAddEnum(String enumKey, String enumValue) {
			Key s = null;
			DynamicEnum.updateOrAddEnum(Key.class, enumKey, new Class[] { String.class }, new Object[] { enumValue });
			s = Key.valueOf(enumKey);
			return s;
		}

		private String key;

		private Key(String key) {
			this.key = key;
		}

		public String getKey() {
			return test + this.key;
		}
	}
}
