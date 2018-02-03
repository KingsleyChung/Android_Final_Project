const mongoose = require('mongoose');
const Schema = mongoose.Schema;

let taskSchema = new Schema({
	userName: {
		type: String,
	},
	photo: {
		type: String,
	},
	taskId: {
		type: String,
		unique: true,
	},
	title: {
		type: String,
	},
	content: {
		type: String,
	},
	taskPosName: {
		type: String,
	},
	taskPosLoc: {
		type: [Number],
	},
	tgPosName: {
		type: String,
	},
	tgPosLoc: {
		type: [Number],
	},
	reward: {
		type: Number,
	},
	tag: {
		type: [String],
	},
	srcUser: {
		type: String,
	},
	dstUser: {
		type: [String],
	},
	acUser: {
		type: String,
	},
	taskState: {
		type: Number,
	},
	kind: {
		type: Boolean,
	},
	date: {
		type: String,
	},
});

taskSchema.index({tgPosLoc:'2dsphere'});
taskSchema.index({taskPosLoc:'2dsphere'});

module.exports = mongoose.model('Task', taskSchema);
