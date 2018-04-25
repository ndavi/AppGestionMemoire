import { BaseEntity } from './../../shared';

export class UserExtra implements BaseEntity {
    constructor(
        public id?: number,
        public promotionId?: number,
        public secteurId?: number,
    ) {
    }
}
