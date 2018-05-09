import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParameterCodec, HttpParams, HttpResponse} from "@angular/common/http";
import {EntityResponseType} from "../entities/secteur/secteur.service";
import {Secteur} from "../entities/secteur/secteur.model";
import {Observable} from "rxjs/Observable";
import {SERVER_API_URL} from "../app.constants";

@Injectable()

export class HomeService {
    constructor(private http: HttpClient) {

    }

    private resourceUrl =  SERVER_API_URL + 'api/memoires/upload';


    uploadMemoire(data: any[]) : Observable<any> {

        return this.http.post<any>(this.resourceUrl, data, { observe: 'response' })

    }
}
