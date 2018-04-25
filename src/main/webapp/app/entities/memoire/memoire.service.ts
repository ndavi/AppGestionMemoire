import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Memoire } from './memoire.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Memoire>;

@Injectable()
export class MemoireService {

    private resourceUrl =  SERVER_API_URL + 'api/memoires';

    constructor(private http: HttpClient) { }

    create(memoire: Memoire): Observable<EntityResponseType> {
        const copy = this.convert(memoire);
        return this.http.post<Memoire>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(memoire: Memoire): Observable<EntityResponseType> {
        const copy = this.convert(memoire);
        return this.http.put<Memoire>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Memoire>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Memoire[]>> {
        const options = createRequestOption(req);
        return this.http.get<Memoire[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Memoire[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Memoire = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Memoire[]>): HttpResponse<Memoire[]> {
        const jsonResponse: Memoire[] = res.body;
        const body: Memoire[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Memoire.
     */
    private convertItemFromServer(memoire: Memoire): Memoire {
        const copy: Memoire = Object.assign({}, memoire);
        return copy;
    }

    /**
     * Convert a Memoire to a JSON which can be sent to the server.
     */
    private convert(memoire: Memoire): Memoire {
        const copy: Memoire = Object.assign({}, memoire);
        return copy;
    }
}
