import {render} from "@testing-library/react";
import ShortJobOffer from "./ShortJobOffer";
import React from "react";
import ShortJobOfferApplication from "./ShortJobOfferApplication";

describe("ShortJobOffer", () => {
    const mockJobOffer = {
        title: "Software Engineer",
        department: "Engineering",
        nbOfCandidates: 5,
        jobOfferState: "ACTIVE",
        id: "123"
    };
    const mockUser = {
        id: "123",
        isLoggedIn: true,
        role: "STUDENT",
        firstName: "John",
        lastName: "Doe",
        email: "",
        phoneNumber: "",
    }
    function handleRefresh() {
        // do something
    }
    const mockIndex = 1;

    beforeEach(() => {
        render(<ShortJobOfferApplication
            index={mockIndex}
            user={mockUser}
            jobOffer={mockJobOffer}
            key={mockJobOffer.id}
            refresh={handleRefresh}/>);
    });
});