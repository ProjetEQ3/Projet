class Session {
    id
    year
    season

    constructor() {
        this.reset()
    }

    init(session) {
        if(session.id) this.id = session.id
        else this.id = undefined
        if(session.year) this.year = session.year
        else this.year = undefined
        if(session.season) this.season = session.season
        else this.season = undefined
    }

    reset() {
        this.id = undefined
        this.year = undefined
        this.season = undefined
    }
}

export default Session;