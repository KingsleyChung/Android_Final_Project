const router = require('koa-router')();
const userController  = require('../controller/user');
const multer = require('koa-multer');
//配置
let storage = multer.diskStorage({
	//文件保存路径
	destination: function (req, file, cb) {
		cb(null, './public/image/');
	},
	filename: function (req, file, cb){
		let fileFormat = (file.originalname).split(".");
		cb(null, Date.now() + "." + fileFormat[fileFormat.length - 1]);
	},
});
let upload = multer({storage:storage});

let User = router
	.post('/register', userController.register)
	.post('/login', userController.login)
	.post('/update', userController.updateUser)
	.post('/money', userController.updateMoney)
	.post('/friend/add', userController.addFriend)
	//.post('/friend/update', userController.updateFriend)
	.post('/friend/delete', userController.deleteFriend)
	.get('/res/alluser', userController.allUserInfo)
	.post('/res/user', userController.userInfo)
	.post('/res/moreuser', userController.moreUserInfo)
	.post('/upload', upload.single('photo'), async (ctx) => {
		ctx.body = {success: true, message: 'The photo upload successfully!', photo: ctx.req.file.filename};

		// let {userName, nickName, password, phone, email, qq, wechat} = ctx.req.body;
		//
		// //判断新建的用户是否在数据库内
		// let user = await userSchema.findOne({userName}).catch(err => {
		// 	ctx.throw(500, 'Error from finding user!');
		// });
		//
		// if (!user) {
		// 	// //修改文件名
		// 	// let format = photo.split(".");
		// 	// let allFormat = '.' + format[format.length - 1];
		// 	// let photoName = userName + allFormat
		// 	// fs.rename('public/image/' + photo, 'public/image/' + photoName, (err) => {
		// 	// 	if (err)
		// 	// 		throw(err);
		// 	// });
		//
		// 	let newUser = new userSchema({
		// 		userName: userName,
		// 		nickName: nickName,
		// 		password: password,
		// 		phone: phone,
		// 		email: email,
		// 		photo: "",
		// 		qq: qq,
		// 		wechat: wechat,
		// 		money: 100.00,
		// 	});
		// 	newUser.userId = newUser._id;
		//
		// 	//保存新建的用户
		// 	await newUser.save().catch(err => {
		// 		ctx.throw(500, 'Error from saving user!');
		// 	});
		//
		// 	ctx.body = newUser;
		// }
		// else
		// 	ctx.body = {success: false, message: 'This user exists!'};
	})

module.exports = User;
