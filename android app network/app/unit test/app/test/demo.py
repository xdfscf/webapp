# coding:utf-8
from flask import Flask, request, jsonify
app = Flask(__name__)
@app.route("/dologin", methods=["POST"])
def login():
    """登录"""
    name = request.form.get("name")
    password = request.form.get("password")
    if not all([name, password]):
        # 如果name或password中有一个为空或者都为空
        return jsonify(code=1, message=u"参数不完整")
    if name == "admin" and password =="python":
        return jsonify(code=0, message=u"OK")
    else:
        return jsonify(code=2, message=u"用户名或密码错误")
if __name__ == '__main__':
    app.run()
