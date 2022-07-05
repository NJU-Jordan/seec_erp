import request from "@/network/request";
const testAPI = require("@/apis")

export const getAllAccount = config => request._get(testAPI.ACCOUNT_ALL, config);
export const createAccount = config => request._post(testAPI.ACCOUNT_CREATE, config);
export const updateAccount = config => request._post(testAPI.ACCOUNT_UPDATE, config);
export const deleteAccount = config => request._get(testAPI.ACCOUNT_DELETE, config);