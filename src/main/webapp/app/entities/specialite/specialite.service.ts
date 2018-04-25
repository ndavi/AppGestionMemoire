import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Specialite } from './specialite.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Specialite>;

@Injectable()
export class SpecialiteService {

    private resourceUrl =  SERVER_API_URL + 'api/specialites';

    constructor(private http: HttpClient) { }

    create(specialite: Specialite): Observable<EntityResponseType> {
        const copy = this.convert(specialite);
        return this.http.post<Specialite>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(specialite: Specialite): Observable<EntityResponseType> {
        const copy = this.convert(specialite);
        return this.http.put<Specialite>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Specialite>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Specialite[]>> {
        const options = createRequestOption(req);
        return this.http.get<Specialite[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Specialite[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Specialite = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Specialite[]>): HttpResponse<Specialite[]> {
        const jsonResponse: Specialite[] = res.body;
        const body: Specialite[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Specialite.
     */
    private convertItemFromServer(specialite: Specialite): Specialite {
        const copy: Specialite = Object.assign({}, specialite);
        return copy;
    }

    /**
     * Convert a Specialite to a JSON which can be sent to the server.
     */
    private convert(specialite: Specialite): Specialite {
        const copy: Specialite = Object.assign({}, specialite);
        return copy;
    }
}
