import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Secteur } from './secteur.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Secteur>;

@Injectable()
export class SecteurService {

    private resourceUrl =  SERVER_API_URL + 'api/secteurs';

    constructor(private http: HttpClient) { }

    create(secteur: Secteur): Observable<EntityResponseType> {
        const copy = this.convert(secteur);
        return this.http.post<Secteur>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(secteur: Secteur): Observable<EntityResponseType> {
        const copy = this.convert(secteur);
        return this.http.put<Secteur>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Secteur>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Secteur[]>> {
        const options = createRequestOption(req);
        return this.http.get<Secteur[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Secteur[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Secteur = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Secteur[]>): HttpResponse<Secteur[]> {
        const jsonResponse: Secteur[] = res.body;
        const body: Secteur[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Secteur.
     */
    private convertItemFromServer(secteur: Secteur): Secteur {
        const copy: Secteur = Object.assign({}, secteur);
        return copy;
    }

    /**
     * Convert a Secteur to a JSON which can be sent to the server.
     */
    private convert(secteur: Secteur): Secteur {
        const copy: Secteur = Object.assign({}, secteur);
        return copy;
    }
}
