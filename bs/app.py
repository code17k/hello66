from flask import Flask, render_template, request, jsonify

app = Flask(__name__)

# 1. 渲染页面的路由：用户访问网址时，把 index.html 发给他
@app.route('/')
def index():
    return render_template('index.html')

# 2. 数据接口路由：前端把参数发到这里，后端计算后返回结果
@app.route('/api/predict', methods=['POST'])
def predict():
    # 获取前端发来的 JSON 数据
    data = request.get_json()
    
    genre = data.get('genre')
    duration = data.get('duration')
    # ... 这里调用你的机器学习模型进行预测 ...
    
    # 模拟预测结果
    result = {
        "views": 150.5,
        "completion": 68.2,
        "engagement": 12.5,
        "fans": 850
    }
    
    # 将结果以 JSON 格式返回给前端
    return jsonify(result)

if __name__ == '__main__':
    app.run(debug=True)

