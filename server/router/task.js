const router = require('koa-router')();
const taskController  = require('../controller/task');

let Task = router
	.post('/add', taskController.add)
	.post('/update', taskController.updateTask)
	.post('/tag', taskController.updateTag)
	.post('/reward', taskController.updateReward)
	//.post('/user', taskController.updateUser)
	.post('/accept', taskController.acceptTask)
	.post('/cancel', taskController.cancelTask)
	.post('/delete', taskController.deleteTask)
	.post('/finish', taskController.finishTask)
	.post('/near', taskController.nearTask)
	.post('/res/task', taskController.taskInfo)
	.post('/res/moretask', taskController.moreTaskInfo)
	.post('/res/pushtask', taskController.pushTaskInfo)
	.post('/res/actask', taskController.acTaskInfo)
	.post('/res/inftask', taskController.infTaskInfo)

module.exports = Task;