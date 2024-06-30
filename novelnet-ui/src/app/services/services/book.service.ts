/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { arrpoveReturnBorrowedBook } from '../fn/book/arrpove-return-borrowed-book';
import { ArrpoveReturnBorrowedBook$Params } from '../fn/book/arrpove-return-borrowed-book';
import { borrowBook } from '../fn/book/borrow-book';
import { BorrowBook$Params } from '../fn/book/borrow-book';
import { findAllBooks } from '../fn/book/find-all-books';
import { FindAllBooks$Params } from '../fn/book/find-all-books';
import { findAllBooksBelongToOwner } from '../fn/book/find-all-books-belong-to-owner';
import { FindAllBooksBelongToOwner$Params } from '../fn/book/find-all-books-belong-to-owner';
import { findAllBorrowedBooksByUser } from '../fn/book/find-all-borrowed-books-by-user';
import { FindAllBorrowedBooksByUser$Params } from '../fn/book/find-all-borrowed-books-by-user';
import { findAllReturnedBooksByUser } from '../fn/book/find-all-returned-books-by-user';
import { FindAllReturnedBooksByUser$Params } from '../fn/book/find-all-returned-books-by-user';
import { getBookById } from '../fn/book/get-book-by-id';
import { GetBookById$Params } from '../fn/book/get-book-by-id';
import { MyBookResponse } from '../models/my-book-response';
import { PageResponseBorrowedBookResponse } from '../models/page-response-borrowed-book-response';
import { PageResponseMyBookResponse } from '../models/page-response-my-book-response';
import { returnBorrowedBook } from '../fn/book/return-borrowed-book';
import { ReturnBorrowedBook$Params } from '../fn/book/return-borrowed-book';
import { saveBook } from '../fn/book/save-book';
import { SaveBook$Params } from '../fn/book/save-book';
import { updateArchiveStatus } from '../fn/book/update-archive-status';
import { UpdateArchiveStatus$Params } from '../fn/book/update-archive-status';
import { updateShareableStatus } from '../fn/book/update-shareable-status';
import { UpdateShareableStatus$Params } from '../fn/book/update-shareable-status';
import { uploadBookCover } from '../fn/book/upload-book-cover';
import { UploadBookCover$Params } from '../fn/book/upload-book-cover';

@Injectable({ providedIn: 'root' })
export class BookService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `saveBook()` */
  static readonly SaveBookPath = '/books/save';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `saveBook()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveBook$Response(params: SaveBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return saveBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `saveBook$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  saveBook(params: SaveBook$Params, context?: HttpContext): Observable<number> {
    return this.saveBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `uploadBookCover()` */
  static readonly UploadBookCoverPath = '/books/cover/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `uploadBookCover()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadBookCover$Response(params: UploadBookCover$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return uploadBookCover(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `uploadBookCover$Response()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadBookCover(params: UploadBookCover$Params, context?: HttpContext): Observable<{
}> {
    return this.uploadBookCover$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `borrowBook()` */
  static readonly BorrowBookPath = '/books/borrow/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `borrowBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  borrowBook$Response(params: BorrowBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return borrowBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `borrowBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  borrowBook(params: BorrowBook$Params, context?: HttpContext): Observable<number> {
    return this.borrowBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `arrpoveReturnBorrowedBook()` */
  static readonly ArrpoveReturnBorrowedBookPath = '/books/borrow/return/approve/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `arrpoveReturnBorrowedBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  arrpoveReturnBorrowedBook$Response(params: ArrpoveReturnBorrowedBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return arrpoveReturnBorrowedBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `arrpoveReturnBorrowedBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  arrpoveReturnBorrowedBook(params: ArrpoveReturnBorrowedBook$Params, context?: HttpContext): Observable<number> {
    return this.arrpoveReturnBorrowedBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `updateShareableStatus()` */
  static readonly UpdateShareableStatusPath = '/books/shareable/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateShareableStatus()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateShareableStatus$Response(params: UpdateShareableStatus$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return updateShareableStatus(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateShareableStatus$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateShareableStatus(params: UpdateShareableStatus$Params, context?: HttpContext): Observable<number> {
    return this.updateShareableStatus$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `returnBorrowedBook()` */
  static readonly ReturnBorrowedBookPath = '/books/return/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `returnBorrowedBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnBorrowedBook$Response(params: ReturnBorrowedBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return returnBorrowedBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `returnBorrowedBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnBorrowedBook(params: ReturnBorrowedBook$Params, context?: HttpContext): Observable<number> {
    return this.returnBorrowedBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `updateArchiveStatus()` */
  static readonly UpdateArchiveStatusPath = '/books/archive/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateArchiveStatus()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateArchiveStatus$Response(params: UpdateArchiveStatus$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return updateArchiveStatus(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateArchiveStatus$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateArchiveStatus(params: UpdateArchiveStatus$Params, context?: HttpContext): Observable<number> {
    return this.updateArchiveStatus$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `findAllBooks()` */
  static readonly FindAllBooksPath = '/books';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllBooks()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllBooks$Response(params?: FindAllBooks$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseMyBookResponse>> {
    return findAllBooks(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllBooks$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllBooks(params?: FindAllBooks$Params, context?: HttpContext): Observable<PageResponseMyBookResponse> {
    return this.findAllBooks$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseMyBookResponse>): PageResponseMyBookResponse => r.body)
    );
  }

  /** Path part for operation `getBookById()` */
  static readonly GetBookByIdPath = '/books/{book-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBookById()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBookById$Response(params: GetBookById$Params, context?: HttpContext): Observable<StrictHttpResponse<MyBookResponse>> {
    return getBookById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBookById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBookById(params: GetBookById$Params, context?: HttpContext): Observable<MyBookResponse> {
    return this.getBookById$Response(params, context).pipe(
      map((r: StrictHttpResponse<MyBookResponse>): MyBookResponse => r.body)
    );
  }

  /** Path part for operation `findAllReturnedBooksByUser()` */
  static readonly FindAllReturnedBooksByUserPath = '/books/returned';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllReturnedBooksByUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllReturnedBooksByUser$Response(params?: FindAllReturnedBooksByUser$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBorrowedBookResponse>> {
    return findAllReturnedBooksByUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllReturnedBooksByUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllReturnedBooksByUser(params?: FindAllReturnedBooksByUser$Params, context?: HttpContext): Observable<PageResponseBorrowedBookResponse> {
    return this.findAllReturnedBooksByUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBorrowedBookResponse>): PageResponseBorrowedBookResponse => r.body)
    );
  }

  /** Path part for operation `findAllBooksBelongToOwner()` */
  static readonly FindAllBooksBelongToOwnerPath = '/books/owner';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllBooksBelongToOwner()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllBooksBelongToOwner$Response(params?: FindAllBooksBelongToOwner$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseMyBookResponse>> {
    return findAllBooksBelongToOwner(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllBooksBelongToOwner$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllBooksBelongToOwner(params?: FindAllBooksBelongToOwner$Params, context?: HttpContext): Observable<PageResponseMyBookResponse> {
    return this.findAllBooksBelongToOwner$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseMyBookResponse>): PageResponseMyBookResponse => r.body)
    );
  }

  /** Path part for operation `findAllBorrowedBooksByUser()` */
  static readonly FindAllBorrowedBooksByUserPath = '/books/borrowed';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `findAllBorrowedBooksByUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllBorrowedBooksByUser$Response(params?: FindAllBorrowedBooksByUser$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBorrowedBookResponse>> {
    return findAllBorrowedBooksByUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `findAllBorrowedBooksByUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  findAllBorrowedBooksByUser(params?: FindAllBorrowedBooksByUser$Params, context?: HttpContext): Observable<PageResponseBorrowedBookResponse> {
    return this.findAllBorrowedBooksByUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBorrowedBookResponse>): PageResponseBorrowedBookResponse => r.body)
    );
  }

}
