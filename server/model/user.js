const mongoose = require('mongoose');
const Schema = mongoose.Schema;

let userSchema = new Schema({
	userId: {
		type: String,
		unique: true,
	},
	userName: {
		type: String,
		unique: true,
	},
	nickName: {
		type: String,
	},
	password: {
		type: String,
	},
	phone: {
		type: Number
	},
	email: {
		type: String,
	},
	photo: {
		type: String,
	},
	qq: {
		type: String,
	},
	wechat: {
		type: String,
	},
	money: {
		type: Number,
	},
	friend: {
		type: [String],
	},
	group: {
		type: [String],
	},
	pushTask: {
		type: [String],
	},
	pullTask: {
		type: [String]
	},
	acTask: {
		type: [String],
	},
	infTask: {
		type: [String],
	},
});

module.exports = mongoose.model('User', userSchema);

