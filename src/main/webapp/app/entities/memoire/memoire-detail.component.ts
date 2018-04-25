import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Memoire } from './memoire.model';
import { MemoireService } from './memoire.service';

@Component({
    selector: 'jhi-memoire-detail',
    templateUrl: './memoire-detail.component.html'
})
export class MemoireDetailComponent implements OnInit, OnDestroy {

    memoire: Memoire;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private memoireService: MemoireService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMemoires();
    }

    load(id) {
        this.memoireService.find(id)
            .subscribe((memoireResponse: HttpResponse<Memoire>) => {
                this.memoire = memoireResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMemoires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'memoireListModification',
            (response) => this.load(this.memoire.id)
        );
    }
}
