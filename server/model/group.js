const mongoose = require('mongoose');
const Schema = mongoose.Schema;

let groupSchema = new Schema({
	groupId: {
		type: String,
		unique: true,
	},
	groupName: {
		type: String,
	},
	groupMember: {
		type: [],
	},
	groupTask: {
		type: [],
	},
});

module.exports = mongoose.model('Group', groupSchema);
