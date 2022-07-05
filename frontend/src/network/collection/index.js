import request from "@/network/request"
const testAPI = require("@/apis")

export const getAllCollection = config => request._get(testAPI.COLLECTION_ALL, config)
export const createCollection = config => request._post(testAPI.COLLECTION_CREATE, config)
export const secondApproval = config => request._get(testAPI.COLLECTION_SECOND_APPROVAL, config)
export const getCollectionBySheetId = config => request._get(testAPI.COLLECTION_FIND_SHEET_BY_ID, config)
export const getAllCustomer = config => request._get(testAPI.CUSTOMER_QUERY, config)