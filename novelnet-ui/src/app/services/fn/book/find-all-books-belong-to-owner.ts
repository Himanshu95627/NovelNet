/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseMyBookResponse } from '../../models/page-response-my-book-response';

export interface FindAllBooksBelongToOwner$Params {
  page?: number;
  size?: number;
}

export function findAllBooksBelongToOwner(http: HttpClient, rootUrl: string, params?: FindAllBooksBelongToOwner$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseMyBookResponse>> {
  const rb = new RequestBuilder(rootUrl, findAllBooksBelongToOwner.PATH, 'get');
  if (params) {
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseMyBookResponse>;
    })
  );
}

findAllBooksBelongToOwner.PATH = '/books/owner';
