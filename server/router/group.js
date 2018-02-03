const router = require('koa-router')();
const groupController  = require('../controller/group');

let Group = router
	.post('/add', groupController.add)
	.post('/user', groupController.updateUser)
	.post('/task', groupController.updateTask)

module.exports = Group;