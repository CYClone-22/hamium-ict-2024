import logging
from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from werkzeug.exceptions import BadRequest
import openai

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://admin:today0430@database-1.cva6yg8oenlg.ap-northeast-2.rds.amazonaws.com/mentosdb'
app.config['SQLALCHEMY_ENGINE_OPTIONS'] = {'pool_pre_ping': True}
db = SQLAlchemy(app)

openai.api_key = 'sk-mentos-qHLdPTiXoYsq42J13McDT3BlbkFJZvXpl4ImtXbSi1eUzFEb'

@app.errorhandler(BadRequest)
def handle_bad_request(e):
    return jsonify({'message': 'Invalid JSON format'}), 400

# 로그 설정
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger('phpsignup')

class Guest(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String(255), unique=True, nullable=False)
    password = db.Column(db.String(255), nullable=False)
    gender = db.Column(db.Enum('male', 'female'))
    birthdate = db.Column(db.Date)

class Survey(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    guest_id = db.Column(db.Integer, db.ForeignKey('guest.id'), nullable=False)
    role = db.Column(db.String(20))
    activity = db.Column(db.String(20))
    experience_level = db.Column(db.Integer)
    gender = db.Column(db.Enum('male', 'female'))
    age_group = db.Column(db.Integer)
    location = db.Column(db.String(50))
    activity_level = db.Column(db.String(20))

# 회원가입 라우트
@app.route('/signup', methods=['GET', 'POST'])
def signup():
    if request.method == 'POST':
        data = request.get_json()
        email = data['email']
        password = data['password']
        gender = data['gender']
        birthdate = data['birthdate']
        
        new_guest = Guest(email=email, password=password, gender=gender, birthdate=birthdate)
        db.session.add(new_guest)
        db.session.commit()
        
        logger.info('User created: %s', email)
        return jsonify({"message": "User created successfully!"}), 201
    else:  # 브라우저는 무조건 -> GET 요청에 대한 처리
        return jsonify({"message": "Please use POST to sign up."}), 200

# 로그인 라우트
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        data = request.get_json()
        email = data.get('email')
        password = data.get('password')
        
        # 이메일 및 비밀번호 존재 여부 확인
        if not email or not password:
            return jsonify({"message": "Email and password are required"}), 400
        
        # 데이터베이스에서 이메일로 사용자 검색
        user = Guest.query.filter_by(email=email).first()
        if user and user.password == password:
            logger.info('Login successful for user: %s', email)
            return jsonify({"message": "Login successful!"}), 200
        else:
            logger.warning('Login failed for user: %s', email)
            return jsonify({"message": "Invalid email or password"}), 401
    
    elif request.method == 'GET':
        # GET 요청에 대한 처리
        return jsonify({"message": "Please use POST to login."}), 200
    
# 설문조사 라우트
@app.route('/survey', methods=['GET', 'POST'])
def survey():
    if request.method == 'POST':
        data = request.get_json()
        guest_id = data['guest_id']
        role = data['role']
        activity = data['activity']
        experience_level = data['experience_level']
        gender = data['gender']
        age_group = data['age_group']
        location = data['location']
        activity_level = data['activity_level']
        
        new_survey = Survey(guest_id=guest_id, role=role, activity=activity, experience_level=experience_level, gender=gender,
                            age_group=age_group, location=location, activity_level=activity_level)
        db.session.add(new_survey)
        db.session.commit()
        
        logger.info('Survey submitted: %s', guest_id)
        return jsonify({"message": "Survey submitted successfully!"}), 201
    else:  # GET 요청에 대한 처리
        return jsonify({"message": "Please use POST to submit a survey."}), 200

def get_openai_response(option):
    try:
        # GPT-3.5 Turbo API 호출
        response = openai.ChatCompletion.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "전문적인 기타 지식을 가진 AI 멘토입니다."},
                {"role": "user", "content": option}
            ]
        )
        return response.choices[0].message['content']
    except Exception as e:
        logger.error('Error in OpenAI API call: %s', str(e))
        raise

@app.route('/chat', methods=['GET', 'POST'])
def chat():
    if request.method == 'POST':
        data = request.get_json()
        option = data.get('option')
    elif request.method == 'GET':
        option = request.args.get('option')
    else:
        return jsonify({"message": "Invalid request method"}), 405

    if not option:
        return jsonify({"message": "Option is required"}), 400

    try:
        assistant_message = get_openai_response(option)
        logger.info('Chat successful with option: %s', option)
        return jsonify({'response': assistant_message}), 200
    except Exception as e:
        logger.error('Error processing chat request: %s', str(e))
        return jsonify({"message": "Error processing the request"}), 500

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5001)
