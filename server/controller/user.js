const fs = require('fs');
const User = require('../model/user');
const Task = require('../model/task');

module.exports = {
	register: async (ctx) => {
		let {userName, nickName, password, phone, email, photo, qq, wechat} = ctx.request.body;

		//判断新建的用户是否在数据库内
		let user = await User.findOne({userName}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		if (!user) {
			//判断文件是否存在
			if (photo !== "") {
				await fs.exists('public/image/' + photo, (exist) => {
					if (exist) {
						//修改文件名
						let format = photo.split(".");
						let photoName = userName + '.jpg';
						fs.rename('public/image/' + photo, 'public/image/' + photoName, (err) => {
							if (err)
								throw(err);
						});
						console.log(photoName);
					}
				});
			}

			let newUser = new User({
				userName: userName,
				nickName: nickName,
				password: password,
				phone: phone,
				email: email,
				photo: photo,
				qq: qq,
				wechat: wechat,
				money: 100.00,
			});
			if (photo !== "") {
				let format = photo.split(".");
				let photoName = userName + '.jpg';
				newUser.photo = photoName;
			}
			newUser.userId = newUser._id;

			//保存新建的用户
			await newUser.save().catch(err => {
				ctx.throw(500, 'Error from saving user!');
			});

			ctx.body = newUser;
		}
		else
			ctx.body = {success: false, message: 'This user exists!'};
	},

	login: async (ctx) => {
		let {userName, password} = ctx.request.body;

		//判断登录的用户是否在数据库内
		let user = await User.findOne({userName}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		if (!user)
			ctx.body = {success: false, message: 'This user does not exist!'};
		else if (password !== user.password) {
			ctx.body = {success: false, message: 'This password is wrong!'};
		}
		else {
			let pushTask = [], acTask = [], infTask = [];
			//返回pushTask具体信息
			for (let val of user.pushTask) {
				let tmp = await Task.findOne({taskId: val}).catch(err => {
					ctx.throw(500, 'Error from finding tasks!');
				});
				pushTask.push(tmp);
			}
			//返回infTask具体信息
			for (let val of user.infTask) {
				let tmp = await Task.findOne({taskId: val}).catch(err => {
					ctx.throw(500, 'Error from finding tasks!');
				});
				infTask.push(tmp);
			}
			//返回acTask具体信息
			for (let val of user.acTask) {
				let tmp = await Task.findOne({taskId: val}).catch(err => {
					ctx.throw(500, 'Error from finding tasks!');
				});
				acTask.push(tmp);
			}
			user.pushTask = pushTask;
			user.infTask = infTask;
			user.acTask = acTask;

			ctx.body = user;
		}
	},

	updateUser: async (ctx) => {
		let {userId, userName, nickName, password, phone, email, photo, qq, wechat} = ctx.request.body;

		//判断更新的用户是否在数据库内
		let user = await User.findOne({userId}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		if (user) {
			user.userName = userName;
			user.nickName = nickName;
			user.password = password;
			user.phone = phone;
			user.email = email;
			user.photo = photo;
			user.qq = qq;
			user.wechat = wechat;

			//保存更新的用户
			await user.save().catch(err => {
				ctx.throw(500, 'Error from saving user!');
			});
			ctx.body = user;
		}
		else
			ctx.body = {success: false, message: 'This user is not exist!'};
	},

	updateMoney: async (ctx) => {
		let {userId, money} = ctx.request.body;

		//判断更新的用户是否在数据库内
		let user = await User.findOne({userId}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		if (user) {
			user.money = money;

			//保存更新的用户
			await user.save().catch(err => {
				ctx.throw(500, 'Error from saving user!');
			});
			ctx.body = user;
		}
		else
			ctx.body = {success: false, message: 'This user is not exist!'};
	},

	addFriend: async (ctx) => {
		let {userId, friendName} = ctx.request.body;

		//判断更新的用户是否在数据库内
		let user = await User.findOne({userId}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		if (user && user.userName !== friendName) {
			//判断朋友是否存在数据库内
			let userFriend = await User.findOne({userName: friendName}).catch(err => {
				ctx.throw(500, 'Error from finding user!');
			});

			if (userFriend) {
				//判断新的朋友是否存在于朋友列表
				let flag = false;
				for (let val of user.friend) {
					if (val === friendName) {
						flag = true;
						break;
					}
				}

				if (!flag) {
					user.friend.push(friendName);
					//保存更新的用户
					await user.save().catch(err => {
						ctx.throw(500, 'Error from saving user!');
					});
				}
				ctx.body = user;
			}
			else
				ctx.body = {success: false, message: 'This friend is not exist!'};
		}
		else
			ctx.body = {success: false, message: 'This user is not exist!'};
	},

	updateFriend: async (ctx) => {
		let {userId, friendName, friendInfo} = ctx.request.body;

		//判断更新的用户是否在数据库内
		let user = await User.findOne({userId}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		if (user) {
			//判断朋友是否存在数据库内
			let userFriend = await User.findOne({userName: friendName}).catch(err => {
				ctx.throw(500, 'Error from finding user!');
			});

			if (userFriend) {
				//判断新的朋友是否存在于朋友列表
				let flag = false;
				for (let val of user.friend) {
					if (val === friend) {
						flag = true;
						break;
					}
				}

				if (!flag) {


					//保存更新的用户
					await user.save().catch(err => {
						ctx.throw(500, 'Error from saving user!');
					});
				}
				ctx.body = user;
			}
			else
				ctx.body = {success: false, message: 'This friend is not exist!'};
		}
		else
			ctx.body = {success: false, message: 'This user is not exist!'};
	},

	deleteFriend: async (ctx) => {
		let {userId, friendName} = ctx.request.body;

		//判断更新的用户是否在数据库内
		let user = await User.findOne({userId}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		if (user && user.userName !== friendName) {
			//判断删除的朋友是否存在于朋友列表
			for (let val of user.friend) {
				//若存在就删除
				if (val === friendName) {
					user.friend.remove(friendName);
					//保存更新的用户
					await user.save().catch(err => {
						ctx.throw(500, 'Error from saving user!');
					});
					break;
				}
			}
			ctx.body = user;
		}
		else
			ctx.body = {success: false, message: 'This user is not exist!'};
	},

	allUserInfo: async (ctx) => {
		let users = await User.find().catch(err => {
			ctx.throw(500, 'Error from finding users!');
		});
		let tasks = await Task.find().catch(err => {
			ctx.throw(500, 'Error from finding tasks!');
		});

		//检查过期的任务
		for (let task of tasks) {
			let date = new Date(task.date).getTime();
			let now = new Date().getTime();
			if (date < now) {
				task.taskState = -1;
				await task.save().catch(err => {
					ctx.throw(500, 'Error from saving task!');
				});
			}
			// if (task.kind === false) {
			// 	for (let user of users) {
			// 		if (task.srcUser !== user.userId)
			// 			user.pullTask.push(task.taskId);
			// 	}
			// }
		}

		//返回各种任务具体信息
		for (let user of users) {
			let pushTask = [], acTask = [], infTask = [];
			//返回pushTask具体信息
			for (let val of user.pushTask) {
				let tmp = await Task.findOne({taskId: val}).catch(err => {
					ctx.throw(500, 'Error from finding tasks!');
				});
				pushTask.push(tmp);
			}
			//返回infTask具体信息
			for (let val of user.infTask) {
				let tmp = await Task.findOne({taskId: val}).catch(err => {
					ctx.throw(500, 'Error from finding tasks!');
				});
				infTask.push(tmp);
			}
			//返回acTask具体信息
			for (let val of user.acTask) {
				let tmp = await Task.findOne({taskId: val}).catch(err => {
					ctx.throw(500, 'Error from finding tasks!');
				});
				acTask.push(tmp);
			}
			user.pushTask = pushTask;
			user.infTask = infTask;
			user.acTask = acTask;
		}

		ctx.body = users;
	},

	userInfo: async (ctx) => {
		let {userName} = ctx.request.body;

		//判断用户是否在数据库内
		let user = await User.findOne({userName}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		if (user) {
			ctx.body = user;
		}
		else
			ctx.body = {success: false, message: 'This user does not exist!'};
	},

	moreUserInfo: async (ctx) => {
		let {userArr} = ctx.request.body;

		let users = [];
		for (let val of userArr) {
			let user = await User.findOne({userName: val}).catch(err => {
				ctx.throw(500, 'Error from finding task!');
			});
			users.push(user);
		}

		ctx.body = users;
	},

};
