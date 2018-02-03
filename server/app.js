const Koa = require('koa');
const path = require('path');
const bodyParser = require('koa-bodyparser');
const json = require('koa-json');
const convert = require('koa-convert');
const view = require('koa-views');
const koaLogger = require('koa-logger');
const koaStatic = require("koa-static");
const mongoose = require('mongoose');
const config = require('./config/config');
const router = require('./router/index');
const app = new Koa();

// 配置控制台日志中间件
app.use(convert(koaLogger()));

//
// // 配置服务端模板渲染引擎中间件
// app.use(view(path.join(__dirname, './view'), {
// 	extension: 'html'
// }));
//
// // 配置静态资源加载中间件
// app.use(convert(koaStatic(
// 	path.join(__dirname , './static')
// )));

//加载静态资源
app.use(koaStatic(__dirname + '/'));//获取图片资源：localhost:8000/public/image + 图片名

// 使用ctx.body解析中间件
app.use(json());
app.use(bodyParser());

mongoose.Promise = global.Promise;
mongoose.connect(config.database);

// 初始化路由中间件
app.use(router.routes()).use(router.allowedMethods());

app.listen(8000);
console.log('port 8000 is listened!');