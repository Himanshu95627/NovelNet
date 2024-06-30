/* tslint:disable */
/* eslint-disable */
import { MyBookResponse } from '../models/my-book-response';
export interface PageResponseMyBookResponse {
  content?: Array<MyBookResponse>;
  first?: boolean;
  last?: boolean;
  number?: number;
  size?: number;
  totalElements?: number;
  totalPages?: number;
}
