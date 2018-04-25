import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Specialite } from './specialite.model';
import { SpecialiteService } from './specialite.service';

@Component({
    selector: 'jhi-specialite-detail',
    templateUrl: './specialite-detail.component.html'
})
export class SpecialiteDetailComponent implements OnInit, OnDestroy {

    specialite: Specialite;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private specialiteService: SpecialiteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSpecialites();
    }

    load(id) {
        this.specialiteService.find(id)
            .subscribe((specialiteResponse: HttpResponse<Specialite>) => {
                this.specialite = specialiteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSpecialites() {
        this.eventSubscriber = this.eventManager.subscribe(
            'specialiteListModification',
            (response) => this.load(this.specialite.id)
        );
    }
}
