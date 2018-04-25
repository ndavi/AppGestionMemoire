import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Promotion } from './promotion.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Promotion>;

@Injectable()
export class PromotionService {

    private resourceUrl =  SERVER_API_URL + 'api/promotions';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(promotion: Promotion): Observable<EntityResponseType> {
        const copy = this.convert(promotion);
        return this.http.post<Promotion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(promotion: Promotion): Observable<EntityResponseType> {
        const copy = this.convert(promotion);
        return this.http.put<Promotion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Promotion>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Promotion[]>> {
        const options = createRequestOption(req);
        return this.http.get<Promotion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Promotion[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Promotion = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Promotion[]>): HttpResponse<Promotion[]> {
        const jsonResponse: Promotion[] = res.body;
        const body: Promotion[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Promotion.
     */
    private convertItemFromServer(promotion: Promotion): Promotion {
        const copy: Promotion = Object.assign({}, promotion);
        copy.datedebut = this.dateUtils
            .convertDateTimeFromServer(promotion.datedebut);
        copy.datefin = this.dateUtils
            .convertDateTimeFromServer(promotion.datefin);
        return copy;
    }

    /**
     * Convert a Promotion to a JSON which can be sent to the server.
     */
    private convert(promotion: Promotion): Promotion {
        const copy: Promotion = Object.assign({}, promotion);

        copy.datedebut = this.dateUtils.toDate(promotion.datedebut);

        copy.datefin = this.dateUtils.toDate(promotion.datefin);
        return copy;
    }
}
