const User = require('../model/user');
const Task = require('../model/task');

module.exports = {
	add: async (ctx) => {
		let {userName, title, content, taskPosName, taskPosLoc, tgPosName, tgPosLoc, tag, reward, kind, date, userId, user} = ctx.request.body;

		let newTask = new Task({
			userName: userName,
			title: title,
			content: content,
			taskPosName: taskPosName,
			taskPosLoc: taskPosLoc,
			tgPosName: tgPosName,
			tgPosLoc: tgPosLoc,
			tag: tag,
			reward: reward,
			kind: kind,
			date: date,
			srcUser: userId,
			taskState: 0,
		});
		if (newTask.kind === true)
			newTask.dstUser = user;
		newTask.taskId = newTask._id;

		//发布者添加发布的任务
		let pushUser = await User.findOne({userId}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});
		pushUser.pushTask.push(newTask.taskId);
		await pushUser.save().catch(err => {
			ctx.throw(500, 'Error from saving user!');
		});

		if (newTask.kind === true) {
			//接受者添加发布的任务
			for (let val of newTask.dstUser) {
				let tmpUser = await User.findOne({userId: val}).catch(err => {
					ctx.throw(500, 'Error from finding user!');
				});
				tmpUser.infTask.push(newTask.taskId);
				await tmpUser.save().catch(err => {
					ctx.throw(500, 'Error from saving user!');
				});
			}
		}

		newTask.photo = pushUser.photo;
		//保存新建的任务
		await newTask.save().catch(err => {
			//console.log(err);
			ctx.throw(500, 'Error from saving task!');
		});

		ctx.body = newTask;
	},

	updateTask: async (ctx) => {
		let {taskId, title, content, taskPosName, taskPosLoc, tgPosName, tgPosLoc, date} = ctx.request.body;

		//判断任务是否在数据库内
		let task = await Task.findOne({taskId}).catch(err => {
			ctx.throw(500, 'Error from finding task!');
		});

		if (task) {
			if (task.taskState === 0) {
				task.title = title;
				task.content = content;
				task.taskPosName = taskPosName;
				task.taskPosLoc = taskPosLoc;
				task.tgPosName = tgPosName;
				task.tgPosLoc = tgPosLoc;
				task.date = date;
				await task.save().catch(err => {
					ctx.throw(500, 'Error from saving task!');
				});
				ctx.body = task;
			}
			else if (task.taskState === 1)
				ctx.body = {success: false, message: 'Can not update task now!'};
			else if (task.taskState === -1)
				ctx.body = {success: false, message: 'This task has been finished!'};
		}
		else
			ctx.body = {success: false, message: 'This task does not exist!'};
	},

	updateTag: async (ctx) => {
		let {taskId, tag} = ctx.request.body;

		//判断任务是否在数据库内
		let task = await Task.findOne({taskId}).catch(err => {
			ctx.throw(500, 'Error from finding task!');
		});

		if (task) {
			if (task.taskState === 0) {
				task.tag = tag;
				await task.save().catch(err => {
					ctx.throw(500, 'Error from saving task!');
				});
				ctx.body = task;
			}
			else if (task.taskState === 1)
				ctx.body = {success: false, message: 'Can not update task now!'};
			else if (task.taskState === -1)
				ctx.body = {success: false, message: 'This task has been finished!'};
		}
		else
			ctx.body = {success: false, message: 'This task does not exist!'};
	},

	updateReward: async (ctx) => {
		let {taskId, reward} = ctx.request.body;

		//判断任务是否在数据库内
		let task = await Task.findOne({taskId}).catch(err => {
			ctx.throw(500, 'Error from finding task!');
		});

		if (task) {
			if (task.taskState === 0) {
				task.reward = reward;
				await task.save().catch(err => {
					ctx.throw(500, 'Error from saving task!');
				});
				ctx.body = task;
			}
			else if (task.taskState === 1)
				ctx.body = {success: false, message: 'Can not update task now!'};
			else if (task.taskState === -1)
				ctx.body = {success: false, message: 'This task has been finished!'};
		}
		else
			ctx.body = {success: false, message: 'This task does not exist!'};
	},

	updateUser: async (ctx) => {
		let {taskId, user} = ctx.request.body;

		//判断任务是否在数据库内
		let task = await Task.findOne({taskId}).catch(err => {
			ctx.throw(500, 'Error from finding task!');
		});

		if (task) {
			if (task.taskState === 0) {
				if (task.srcUser !== user) {
					for (let val of user) {
						if (task.srcUser.indexOf(val) === -1) {
							let tmpUser = await User.findOne({userId: task.srcUser.userId}).catch(err => {
								ctx.throw(500, 'Error from finding user!');
							});
							tmpUser.infTask.push(task);
							await User.save().catch(err => {
								ctx.throw(500, 'Error from saving user!');
							});
						}
					}

					task.dstUser = user;
					await task.save().catch(err => {
						ctx.throw(500, 'Error from saving task!');
					});
					ctx.body = task;
				}
				else
					ctx.body = {success: false, message: 'You can not accept your task!'};
			}
			else if (task.taskState === 1)
				ctx.body = {success: false, message: 'Can not update task now!'};
			else if (task.taskState === -1)
				ctx.body = {success: false, message: 'This task has been finished!'};
		}
		else
			ctx.body = {success: false, message: 'This task does not exist!'};
	},

	acceptTask: async (ctx) => {
		let {userId, taskId} = ctx.request.body;

		//判断任务是否在数据库内
		let task = await Task.findOne({taskId}).catch(err => {
			ctx.throw(500, 'Error from finding task!');
		});

		if (task) {
			if (task.taskState === 0) {
				let user = await User.findOne({userId}).catch(err => {
					ctx.throw(500, 'Error from finding user!');
				});

				if (user) {
					user.acTask.push(task.taskId);
					await user.save().catch(err => {
						ctx.throw(500, 'Error from saving user!');
					});

					task.acUser = user.userName;
					task.taskState = 1;
					await task.save().catch(err => {
						ctx.throw(500, 'Error from saving task!');
					});
					ctx.body = user;
				}
				else
					ctx.body = {success: false, message: 'This user does not exist!'};
			}
			else if (task.taskState === 1)
				ctx.body = {success: false, message: 'This task has been accepted!'};
			else if (task.taskState === -1)
				ctx.body = {success: false, message: 'This task has been finished!'};
		}
		else
			ctx.body = {success: false, message: 'This task does not exist!'};
	},

	cancelTask: async (ctx) => {
		let {userId, taskId} = ctx.request.body;

		//判断任务是否在数据库内
		let task = await Task.findOne({taskId}).catch(err => {
			ctx.throw(500, 'Error from finding task!');
		});

		if (task) {
			if (task.taskState === 1) {
				let user = await User.findOne({userId}).catch(err => {
					ctx.throw(500, 'Error from finding user!');
				});

				if (user) {
					//用户取消任务
					for (let val of user.acTask) {
						//若存在就删除
						if (val === task.taskId) {
							user.acTask.remove(task.taskId);
							//保存更新的用户
							await user.save().catch(err => {
								ctx.throw(500, 'Error from saving user!');
							});
							break;
						}
					}

					//任务取消接受的用户
					task.acUser = "";
					task.taskState = 0;
					await task.save().catch(err => {
						ctx.throw(500, 'Error from saving task!');
					});
					ctx.body = user;
				}
				else
					ctx.body = {success: false, message: 'This user does not exist!'};
			}
			else if (task.taskState === 0)
				ctx.body = {success: false, message: 'This task has not been accepted!'};
			else if (task.taskState === -1)
				ctx.body = {success: false, message: 'This task has been finished!'};
		}
		else
			ctx.body = {success: false, message: 'This task does not exist!'};
	},

	deleteTask: async (ctx) => {
		let {taskId} = ctx.request.body;

		//判断任务是否在数据库内
		let task = await Task.findOne({taskId}).catch(err => {
			ctx.throw(500, 'Error from finding task!');
		});

		if (task) {
			if (task.taskState === 0) {
				let pushUser = await User.findOne({userId: task.srcUser}).catch(err => {
					ctx.throw(500, 'Error from finding user!');
				});
				pushUser.pushTask.remove(task.taskId);
				await pushUser.save().catch(err => {
					ctx.throw(500, 'Error from saving user!');
				});

				for (let val of task.dstUser) {
					let pullUser = await User.findOne({userId: val}).catch(err => {
						ctx.throw(500, 'Error from finding user!');
					});
					pullUser.pullTask.remove(task);
					await pullUser.save().catch(err => {
						ctx.throw(500, 'Error from saving user!');
					});
				}

				await Task.findByIdAndRemove(taskId).catch(err => {
					this.throw(500, '服务器内部错误');
				});

				ctx.body = pushUser;
			}
			else if (task.taskState === 1)
				ctx.body = {success: false, message: 'This task has been accepted!'};
			else if (task.taskState === -1)
				ctx.body = {success: false, message: 'This task has been finished!'};
		}
		else
			ctx.body = {success: false, message: 'This task does not exist!'};
	},

	finishTask: async (ctx) => {
		let {taskId} = ctx.request.body;

		//判断任务是否在数据库内
		let task = await Task.findOne({taskId}).catch(err => {
			ctx.throw(500, 'Error from finding task!');
		});

		if (task) {
			if (task.taskState === 0) {
				task.taskState = -1;
				await task.save().catch(err => {
					ctx.throw(500, 'Error from saving task!');
				});

				//发布者扣钱
				let pushUser = await User.findOne({userId: task.srcUser}).catch(err => {
					ctx.throw(500, 'Error from finding user!');
				});
				pushUser.money -= task.reward;
				await pushUser.save().catch(err => {
					ctx.throw(500, 'Error from saving user!');
				});

				//接受者收钱
				let acUser = await User.findOne({userId: task.acUser}).catch(err => {
					ctx.throw(500, 'Error from finding user!');
				});
				acUser.money += task.reward;
				await acUser.save().catch(err => {
					ctx.throw(500, 'Error from saving user!');
				});

				ctx.body = task;
			}
			else if (task.taskState === 1)
				ctx.body = {success: false, message: 'This task has been accepted!'};
			else if (task.taskState === -1)
				ctx.body = {success: false, message: 'This task has been finished!'};
		}
		else
			ctx.body = {success: false, message: 'This task does not exist!'};
	},

	nearTask: async (ctx) => {
		let {location} = ctx.request.body;

		console.log(location);

		let neartask = await Task.find({
			taskPosLoc: {
				$nearSphere: {
					$geometry: {
						type : "Point",
						coordinates : location
					},
					$maxDistance:  5000   //从近到远返回5000米内的所有任务
				}
			}
		}).catch(err => {
			ctx.throw(500, 'Error from finding near task!');
		});

		ctx.body = neartask;
	},

	taskInfo: async (ctx) => {
		let {taskId} = ctx.request.body;

		//判断任务是否在数据库内
		let task = await Task.findOne({taskId}).catch(err => {
			ctx.throw(500, 'Error from finding task!');
		});

		if (task) {
			ctx.body = task;
		}
		else
			ctx.body = {success: false, message: 'This task does not exist!'};
	},

	moreTaskInfo: async (ctx) => {
		let {taskArr} = ctx.request.body;

		let tasks = [];
		for (let val of taskArr) {
			let task = await Task.findOne({taskId: val}).catch(err => {
				ctx.throw(500, 'Error from finding task!');
			});
			tasks.push(task);
		}

		ctx.body = tasks;
	},

	pushTaskInfo: async (ctx) => {
		let {userName} = ctx.request.body;

		let user = await User.findOne({userName}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		let pushTask = [];
		for (let val of user.pushTask) {
			let task = await Task.findOne({taskId: val}).catch(err => {
				ctx.throw(500, 'Error from finding task!');
			});

			//检查任务是否过期
			let date = new Date(task.date).getTime();
			let now = new Date().getTime();
			if (date < now) {
				task.taskState = -1;
				await task.save().catch(err => {
					ctx.throw(500, 'Error from saving task!');
				});
			}

			pushTask.push(task);
		}

		ctx.body = pushTask;
	},

	acTaskInfo: async (ctx) => {
		let {userName} = ctx.request.body;

		let user = await User.findOne({userName}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		let acTask = [];
		for (let val of user.acTask) {
			let task = await Task.findOne({taskId: val}).catch(err => {
				ctx.throw(500, 'Error from finding task!');
			});

			//检查任务是否过期
			let date = new Date(task.date).getTime();
			let now = new Date().getTime();
			if (date < now) {
				task.taskState = -1;
				await task.save().catch(err => {
					ctx.throw(500, 'Error from saving task!');
				});
			}

			acTask.push(task);
		}

		ctx.body = acTask;
	},

	infTaskInfo: async (ctx) => {
		let {userName} = ctx.request.body;

		let user = await User.findOne({userName}).catch(err => {
			ctx.throw(500, 'Error from finding user!');
		});

		let infTask = [];
		for (let val of user.infTask) {
			let task = await Task.findOne({taskId: val}).catch(err => {
				ctx.throw(500, 'Error from finding task!');
			});

			//检查任务是否过期
			let date = new Date(task.date).getTime();
			let now = new Date().getTime();
			if (date < now) {
				task.taskState = -1;
				await task.save().catch(err => {
					ctx.throw(500, 'Error from saving task!');
				});
			}

			infTask.push(task);
		}

		ctx.body = infTask;
	},

};
