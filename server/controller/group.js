const Group = require('../model/group');
const User = require('../model/user');
const Task = require('../model/task');

module.exports = {
	add: async (ctx) => {
		let {userId, groupName} = ctx.request.body;
		let user = await User.findOne({userId}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		if (user) {
			let newGroup = new Group({groupName: groupName});
			newGroup.groupId = newGroup._id;
			user.group.push(newGroup.groupId);
			await user.save().catch(err => {
				ctx.throw(500, 'Error from saving user!');
			});

			await newGroup.save().catch(err => {
				ctx.throw(500, 'Error from saving group!');
			});

			ctx.body = {success: true, message: 'Add group successfully!', group: newGroup};
		}
		else
			ctx.body = {success: false, message: 'This user does not exist!'};
	},

	updateUser: async (ctx) => {
		let {groupId, user} = ctx.request.body;

		let group = await Group.findOne({groupId}).catch(err => {
			ctx.throw(500, 'Error from finding group!');
		});

		if (group) {
			group.groupMember = user;

			await group.save().catch(err => {
				ctx.throw(500, 'Error from saving group!');
			});
			ctx.body = {success: true, message: 'Add group member is succeed!', group: group};
		}
		else
			ctx.body = {success: false, message: 'This group does not exist!'};
	},

	updateTask: async (ctx) => {

	},

};