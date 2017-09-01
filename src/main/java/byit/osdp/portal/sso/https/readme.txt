https包下代码为通过https协议与服务端通信
调用方法：
	将user对象传递过来，通过HtttpConnHelper的verifyLogin()方法调用
	Map<String, String> map = LoginHtttpsHelper.verifyLogin(_user, "");